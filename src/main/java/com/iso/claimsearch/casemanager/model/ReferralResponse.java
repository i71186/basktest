package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReferralResponse implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 5810464236407102862L;

  private String status;
  private String description;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



}
