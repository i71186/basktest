package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class has mapping with JSON that will be returned to the calling client.
 * 
 * @author i94536
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResponse implements Serializable {



  /**
	 * 
	 */
  private static final long serialVersionUID = 3476739667348668937L;
  /**
	 * 
	 */

  private String status;

  private String statusCode;

  private String statusDesc;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }



}
