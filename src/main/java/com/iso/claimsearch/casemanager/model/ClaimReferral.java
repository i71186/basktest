package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.sql.Date;



public class ClaimReferral implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -1406257773837785985L;


  private String claimNumber;
  private String policyNumber;
  private Date dateOfLoss;
  private String allClaimId;
  private String lolAddress;
  private String lolCity;
  private String lolState;
  private String companyId;

  public String getClaimNumber() {
    return claimNumber;
  }

  public void setClaimNumber(String claimNumber) {
    this.claimNumber = claimNumber;
  }

  public String getPolicyNumber() {
    return policyNumber;
  }

  public void setPolicyNumber(String policyNumber) {
    this.policyNumber = policyNumber;
  }

  public Date getDateOfLoss() {
    return dateOfLoss;
  }

  public void setDateOfLoss(Date dateOfLoss) {
    this.dateOfLoss = dateOfLoss;
  }

  public String getAllClaimId() {
    return allClaimId;
  }

  public void setAllClaimId(String allClaimId) {
    this.allClaimId = allClaimId;
  }

  public String getLolAddress() {
    return lolAddress;
  }

  public void setLolAddress(String lolAddress) {
    this.lolAddress = lolAddress;
  }

  public String getLolCity() {
    return lolCity;
  }

  public void setLolCity(String lolCity) {
    this.lolCity = lolCity;
  }

  public String getLolState() {
    return lolState;
  }

  public void setLolState(String lolState) {
    this.lolState = lolState;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
