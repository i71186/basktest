package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class has mapping with JSON that will be returned to the calling client.
 * 
 * @author i94536
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseEntityResponse implements Serializable {



  /**
	 * 
	 */
  private static final long serialVersionUID = 8994673108236008420L;
  private String status;
  private String caseNumber;
  private String lastUpdated;
  private String city;
  private String state;

  private List<CaseEntity> caseEntities = new ArrayList<CaseEntity>();

  public String getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(String caseNumber) {
    this.caseNumber = caseNumber;
  }

  public List<CaseEntity> getCaseEntities() {
    return caseEntities;
  }

  public void setCaseEntities(List<CaseEntity> caseEntities) {
    this.caseEntities = caseEntities;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
