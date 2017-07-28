package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.iso.claimsearch.casemanager.web.JsonDateDeserializer;

public class ReferralFormRequest implements Serializable {

  private static final long serialVersionUID = -1287809761389957801L;

  @Length(max = 4, min = 4, message = "company Id must be 4 characters ")
  // if form is not sending,remove this
  private String companyId;

  // Reporter Information
  @Length(max = 5, min = 5, message = "userId must be 4 characters ")
  private String userId;

  private String reporterFirstName;

  private String reporterLastName;

  @Email(message = "Email must be valid")
  private String reporterEmail;

  // General Information

  @Length(max = 4, min = 4, message = "loss SubType must be 4 characters ")
  private String lossSubType;

  @Length(max = 4, min = 4, message = "Source Location must be 4 characters ")
  private String sourceLocation;

  @Length(max = 4, min = 4, message = "loss Type must be 4 characters ")
  private String lossType;

  private String group;

  @Length(max = 30, message = "Policy Number must not be more than 30 characters ")
  private String policyNumber;

  private String policyYear;

  @Length(max = 30, message = "Referral reason text must not be more than 30 characters ")
  private String claimNumber;

  @JsonDeserialize(using = JsonDateDeserializer.class)
  @Past(message = "Date of loss cannot be a future date")
  private Date dateOfLoss;

  // Allegation Information
  @NotBlank
  @Length(max = 4, min = 4, message = "Referral reason must be 4 characters ")
  private String referralReason;

  @NotBlank
  private String referralReasonText;

  private List<ReferralEntity> referralEntities = new LinkedList<ReferralEntity>();

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getReporterFirstName() {
    return reporterFirstName == null ? "" : reporterFirstName;
  }

  public void setReporterFirstName(String reporterFirstName) {
    this.reporterFirstName = reporterFirstName;
  }

  public String getReporterLastName() {
    return reporterLastName == null ? "" : reporterLastName;
  }

  public void setReporterLastName(String reporterLastName) {
    this.reporterLastName = reporterLastName;
  }

  public String getReporterEmail() {
    return reporterEmail == null ? "" : reporterEmail;
  }

  public void setReporterEmail(String reporterEmail) {
    this.reporterEmail = reporterEmail;
  }

  public String getLossSubType() {
    return lossSubType;
  }

  public void setLossSubType(String lossSubType) {
    this.lossSubType = lossSubType;
  }

  public String getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(String sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getPolicyNumber() {
    return policyNumber;
  }

  public void setPolicyNumber(String policy) {
    this.policyNumber = policy;
  }

  public String getPolicyYear() {
    return policyYear;
  }

  public void setPolicyYear(String policyYear) {
    this.policyYear = policyYear;
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

  public String getReferralReason() {
    return referralReason;
  }

  public void setReferralReason(String referralReason) {
    this.referralReason = referralReason;
  }

  public String getReferralReasonText() {
    return referralReasonText;
  }

  public void setReferralReasonText(String referralReasonText) {
    this.referralReasonText = referralReasonText;
  }

  public List<ReferralEntity> getReferralEntities() {
    return referralEntities;
  }

  public void setReferralEntities(List<ReferralEntity> referralEntities) {
    this.referralEntities = referralEntities;
  }

  public String getFullName() {
    return getReporterFirstName() + " " + getReporterLastName();
  }

  public String getLossType() {
    return lossType;
  }

  public void setLossType(String lossType) {
    this.lossType = lossType;
  }
}
