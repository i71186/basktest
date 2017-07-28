package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

public class ReferralRequest implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -1855219934601701052L;

  private String claimId;
  private String companyId;
  private String userId;

  public String getClaimId() {
    return claimId;
  }

  public void setClaimId(String claimId) {
    this.claimId = claimId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
