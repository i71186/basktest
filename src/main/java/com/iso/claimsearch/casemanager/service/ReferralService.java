package com.iso.claimsearch.casemanager.service;

import com.iso.claimsearch.casemanager.model.ClaimReferral;
import com.iso.claimsearch.casemanager.model.Referral;

public interface ReferralService {
  public ClaimReferral getClaimReferral(String allClaimId, String companyCode);

  public String addReferral(Referral referral);

  public String getCompanyByClaimId(String allClaimId);

  String addReferralSCIF(Referral referral);
}
