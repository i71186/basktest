package com.iso.claimsearch.casemanager.service;

import java.util.List;

import com.iso.claimsearch.casemanager.model.ReferralEntity;

public interface ReferralEntityService {

  public List<ReferralEntity> getReferralEntities(String allClaimId);

  public List getEntities(List<ReferralEntity> referralEntityList, String companyId);

}
