package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class represents Claim Search Case Manager Case.
 * 
 * @author i90845
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyCase implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = 5526581259849887427L;
  private Integer caseId;
  private String caseNumber;
  private String city;
  private String state;
  private String description;
  private String lastUpdated;
  private Name insured;

  public Integer getCaseId() {
    return caseId;
  }

  public void setCaseId(Integer caseId) {
    this.caseId = caseId;
  }

  public String getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(String caseNumber) {
    this.caseNumber = caseNumber;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public Name getInsured() {
    return insured;
  }

  public void setInsured(Name insured) {
    this.insured = insured;
  }



}
