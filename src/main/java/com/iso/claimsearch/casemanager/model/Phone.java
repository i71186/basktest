package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

public final class Phone implements Serializable {

  private static final long serialVersionUID = 5947013754827626479L;

  private Integer referralId;

  private Integer referralEntityId;

  private String type;

  private String companyId;

  private int areaCode;

  private int number;

  private int pin;

  private String role;

  private boolean subjectOfInvestigation;

  public Integer getReferralId() {
    return referralId;
  }

  public void setReferralId(Integer referralId) {
    this.referralId = referralId;
  }

  public Integer getReferralEntityId() {
    return referralEntityId;
  }

  public void setReferralEntityId(Integer referralEntityId) {
    this.referralEntityId = referralEntityId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public int getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(int areaCode) {
    this.areaCode = areaCode;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getPin() {
    return pin;
  }

  public void setPin(int pin) {
    this.pin = pin;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isSubjectOfInvestigation() {
    return subjectOfInvestigation;
  }

  public void setSubjectOfInvestigation(boolean subjectOfInvestigation) {
    this.subjectOfInvestigation = subjectOfInvestigation;
  }
}
