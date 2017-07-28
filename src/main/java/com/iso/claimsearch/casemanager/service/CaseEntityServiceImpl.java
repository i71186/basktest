package com.iso.claimsearch.casemanager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.dao.CaseDao;
import com.iso.claimsearch.casemanager.model.CaseDetails;
import com.iso.claimsearch.casemanager.model.CaseEntity;

/**
 * This class is responsible for retrieving case entity related information.
 * 
 * @author i94536
 */

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CaseEntityServiceImpl implements CaseEntityService {
  private static final Logger LOGGER = Logger.getLogger(CaseEntityServiceImpl.class);

  @Autowired
  private CaseDao caseDao;

  /**
   * Loads list of case entities via DAO layer.
   * 
   * @return List of My Cases.
   */
  @Override
  public List<CaseEntity> getCaseEntities(String caseId, String companyId) {
    return caseDao.getCaseEntities(caseId, companyId);
  }

  public CaseDetails getCaseDetails(String caseId) {
    return caseDao.getCaseDetails(caseId);
  }
}
