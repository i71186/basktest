package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class has mapping with JSON that will be returned to the calling client.
 * 
 * @author i90845
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyCasesResponse implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = -2339392929489066759L;

  private String status;

  private List<MyCase> myOpenCases = new ArrayList<MyCase>();

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<MyCase> getMyOpenCases() {
    return myOpenCases;
  }

  public void setMyOpenCases(List<MyCase> myOpenCases) {
    this.myOpenCases = myOpenCases;
  }



}
