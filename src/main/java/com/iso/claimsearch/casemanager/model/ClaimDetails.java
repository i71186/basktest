package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClaimDetails implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6936023672304595809L;

  ClaimReferral claimReferral = new ClaimReferral();
  List<ReferralEntity> referralEntityList = new ArrayList<>();


  public ClaimReferral getClaimReferral() {
    return claimReferral;
  }

  public void setClaimReferral(ClaimReferral claimReferral) {
    this.claimReferral = claimReferral;
  }

  public List<ReferralEntity> getReferralEntityList() {
    return referralEntityList;
  }

  public void setReferralEntityList(List<ReferralEntity> referralEntityList) {
    this.referralEntityList = referralEntityList;
  }



}
