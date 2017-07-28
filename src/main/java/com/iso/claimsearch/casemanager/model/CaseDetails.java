package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class represents Claim Search Case Manager Case Details.
 * 
 * @author i90845
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseDetails implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = 4043822571974842970L;
  /**
	 * 
	 */

  private Integer caseId;
  private String caseNumber;
  private String lastUpdated;
  private String city;
  private String state;

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

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
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

}
