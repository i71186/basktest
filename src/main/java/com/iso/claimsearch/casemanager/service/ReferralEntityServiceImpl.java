package com.iso.claimsearch.casemanager.service;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.dao.ReferralDao;
import com.iso.claimsearch.casemanager.model.ReferralEntity;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralEntityServiceImpl implements ReferralEntityService {

  @Autowired
  private ReferralDao referralDao;

  @Override
  public List<ReferralEntity> getReferralEntities(String allClaimId) {
    return referralDao.getReferralEntities(allClaimId);
  }

  public List getEntities(List<ReferralEntity> referralEntityList, String companyId) {

    List entities = new LinkedList();

    List ipObjects = referralEntityList;
    String compID = companyId;
    if (ipObjects.size() > 0) {
      // if(LOGGER.isDebugEnabled()){ LOGGER.debug("InvolvedParty.size():"+ipObjects.size()); }

      // Establish AKA/SP relationships
      ListIterator entityIt = ipObjects.listIterator();


      // Iterate looking for Entities
      while (entityIt.hasNext()) {
        ReferralEntity ipEntity = (ReferralEntity) entityIt.next();
        String notes = ipEntity.getNotes();
        ipEntity.setCompanyId(compID);
        if (notes.indexOf("AKA") == -1 && notes.indexOf("SP") == -1 && !"".equals(notes)) {
          ListIterator subIt = ipObjects.listIterator();
          List akas = new LinkedList();
          List sps = new LinkedList();

          // Iterate looking for AKA's & SP's
          while (subIt.hasNext()) {
            ReferralEntity subEntity = (ReferralEntity) subIt.next();
            String subNotes = subEntity.getNotes();
            subEntity.setCompanyId(compID);
            if (subNotes.indexOf("AKA") > -1) {
              if (notes.equals(subNotes.substring(subNotes.length() - 1, subNotes.length()))) {
                // add to aka
                subEntity.setType("0002");
                subEntity.setSource("Initial Referral");
                subEntity.setNotes("");
                akas.add(subEntity);
              }
            } else if (subNotes.indexOf("SP") > -1) {
              if (notes.equals(subNotes.substring(subNotes.length() - 1, subNotes.length()))) {
                ListIterator subSubIt = ipObjects.listIterator();
                List spAkas = new LinkedList();

                // Iterate looking for SP AKA's
                while (subSubIt.hasNext()) {
                  ReferralEntity subSubEntity = (ReferralEntity) subSubIt.next();
                  String subSubNotes = subSubEntity.getNotes();
                  subSubEntity.setCompanyId(compID);
                  if (subSubNotes.indexOf("AKA") > -1) {
                    if (subNotes.substring(0, 1).equals(
                        subSubNotes.substring(subSubNotes.length() - 1, subSubNotes.length()))) {
                      // add to aka
                      subSubEntity.setType("0002");
                      subSubEntity.setSource("Initial Referral");
                      subSubEntity.setNotes("");
                      spAkas.add(subSubEntity);
                    }
                  }
                }
                subEntity.setAkas(spAkas);

                // add to sp
                subEntity.setType("0003");
                subEntity.setSource("Initial Referral");
                subEntity.setNotes("");
                sps.add(subEntity);
              }
            }
          }

          // Set Entities
          ipEntity.setAkas(akas);
          ipEntity.setServiceProviders(sps);
          ipEntity.setNotes("");
          ipEntity.setType("0001");
          ipEntity.setSource("Initial Referral");

          entities.add(ipEntity);
        }
      }

    }

    return entities;
  }

}
