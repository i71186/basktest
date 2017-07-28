package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Referral implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6993946367058073840L;

  private String allClaimId;
  private String companyId;
  private String referralNumber;
  private String source;
  private String managerQueue;
  // private String referralEdit;
  private Date dateReceived;
  private String city;
  private String state;
  private String county;
  private String department;
  private String businessUnit;
  private String office;
  private String policyNumber;
  private String agentName;
  private String agentAgency;
  private String agentPhone;
  private String agentEmail;
  private String suspicionReason;
  private String referralStatus;
  private String createdBy;
  private String claimNumber;
  private Date dateOfLoss;
  private String lossType;
  private String lossSubType;
  private String lolAddress;
  private String lolCity;
  private String lolState;
  private String referralType;
  private String referralId;
  private String reasonText;
  private Date dateAssigned;
  private String policyYear;
  private String group;
  private List<ReferralEntity> referralEntities = new LinkedList<ReferralEntity>();


  public String getAllClaimId() {
    return allClaimId;
  }

  public void setAllClaimId(String allClaimId) {
    this.allClaimId = allClaimId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getReferralNumber() {
    return referralNumber;
  }

  public void setReferralNumber(String referralNumber) {
    this.referralNumber = referralNumber;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getManagerQueue() {
    return managerQueue;
  }

  public void setManagerQueue(String managerQueue) {
    this.managerQueue = managerQueue;
  }

  public void setDateReceived(Date dateReceived) {
    this.dateReceived = dateReceived;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCounty() {
    return county;
  }

  public Date getDateReceived() {
    return dateReceived;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getBusinessUnit() {
    return businessUnit;
  }

  public void setBusinessUnit(String businessUnit) {
    this.businessUnit = businessUnit;
  }

  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  public String getPolicyNumber() {
    return policyNumber;
  }

  public void setPolicyNumber(String policyNumber) {
    this.policyNumber = policyNumber;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public String getAgentAgency() {
    return agentAgency;
  }

  public void setAgentAgency(String agentAgency) {
    this.agentAgency = agentAgency;
  }

  public String getAgentPhone() {
    return agentPhone;
  }

  public void setAgentPhone(String agentPhone) {
    this.agentPhone = agentPhone;
  }

  public String getAgentEmail() {
    return agentEmail;
  }

  public void setAgentEmail(String agentEmail) {
    this.agentEmail = agentEmail;
  }

  public String getSuspicionReason() {
    return suspicionReason;
  }

  public void setSuspicionReason(String suspicionReason) {
    this.suspicionReason = suspicionReason;
  }

  public String getReferralStatus() {
    return referralStatus;
  }

  public void setReferralStatus(String referralStatus) {
    this.referralStatus = referralStatus;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getClaimNumber() {
    return claimNumber;
  }

  public void setClaimNumber(String claimNumber) {
    this.claimNumber = claimNumber;
  }

  public Date getDateOfLoss() {
    return dateOfLoss;
  }

  public void setDateOfLoss(Date dateOfLoss) {
    this.dateOfLoss = dateOfLoss;
  }

  public String getLossType() {
    return lossType;
  }

  public void setLossType(String lossType) {
    this.lossType = lossType;
  }

  public String getLossSubType() {
    return lossSubType;
  }

  public void setLossSubType(String lossSubType) {
    this.lossSubType = lossSubType;
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

  public String getReferralType() {
    return referralType;
  }

  public void setReferralType(String referralType) {
    this.referralType = referralType;
  }

  public String getReferralId() {
    return referralId;
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public void setReasonText(String reasonText) {
    this.reasonText = reasonText;
  }

  /**
   * @return Returns the reasonText.
   */
  public String getReasonText() {
    if (reasonText != null) {
      reasonText = reasonText.replaceAll("\\<HTML\\>", "");
      reasonText = reasonText.replaceAll("\\<\\/HTML\\>", "");
      reasonText = reasonText.replaceAll("\\<BODY\\>", "");
      reasonText = reasonText.replaceAll("\\<\\/BODY\\>", "");
      return reasonText;
    } else {
      return null; //
    }
  }


  public Date getDateAssigned() {
    return dateAssigned;
  }

  public void setDateAssigned(Date dateAssigned) {
    this.dateAssigned = dateAssigned;
  }

  public String getPolicyYear() {
    return policyYear;
  }

  public void setPolicyYear(String policyYear) {
    this.policyYear = policyYear;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public List<ReferralEntity> getReferralEntities() {
    return referralEntities;
  }

  public void setReferralEntities(List<ReferralEntity> referralEntities) {
    this.referralEntities = referralEntities;
  }

}
