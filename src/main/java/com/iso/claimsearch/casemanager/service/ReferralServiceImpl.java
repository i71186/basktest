package com.iso.claimsearch.casemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.dao.ReferralDao;
import com.iso.claimsearch.casemanager.model.ClaimReferral;
import com.iso.claimsearch.casemanager.model.Referral;
import com.iso.claimsearch.casemanager.util.ReferralUtil;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReferralServiceImpl implements ReferralService {

  private static final String NUMBER_GEN_CODE = "SIU_REF_GEN";

  private static final String ASSURANT_TYPE = "CNUN";

  private static final String SCIF_TYPE = "CDSQ";

  @Autowired
  private ReferralDao referralDao;

  @Override
  public ClaimReferral getClaimReferral(String allClaimId, String companyCode) {
    return referralDao.getClaimReferral(allClaimId, companyCode);
  }

  @Override
  public String addReferral(Referral referral) {
    String univTyp =
        referralDao.getUnivValueByCompIdAndType(referral.getCompanyId(), NUMBER_GEN_CODE);

    if (univTyp.equals(ASSURANT_TYPE)) {

      int refCnt =
          referralDao.getCountByClaimNumber(referral.getCompanyId(), referral.getClaimNumber());
      String referralNumber =
          ReferralUtil.generateCNUNClaimRefferalNumber(refCnt, referral.getClaimNumber());
      if (null != referralNumber) {
        referral.setReferralNumber(referralNumber);
      }
    }
    return referralDao.addReferral(referral);
  }

  @Override
  public String addReferralSCIF(Referral referral) {
    String univTyp =
        referralDao.getUnivValueByCompIdAndType(referral.getCompanyId(), NUMBER_GEN_CODE);
    if (univTyp.equals(SCIF_TYPE)) {
      String referralNumber =
          referralDao.generateReferralNumberFromCustomerSequence(referral.getCompanyId());
      if (null != referralNumber) {
        referral.setReferralNumber(referralNumber);
      }
    }
    return referralDao.addReferralSCIF(referral);
  }

  @Override
  public String getCompanyByClaimId(String allClaimId) {
    return referralDao.getCompanyByClaimId(allClaimId);
  }

  public void setReferralDao(ReferralDao referralDao) {
    this.referralDao = referralDao;
  }
}
