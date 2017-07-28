package com.iso.claimsearch.casemanager.web;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.iso.claimsearch.casemanager.exception.CaseManagerFieldError;

/**
 * This class represents the list of validation errors with status while adding an Activity.
 * 
 * @author i90845
 */

@JsonPropertyOrder({"status", "caseManagerFieldErrors"})
public class CaseManagerValidationError {

  private String status;

  private List<CaseManagerFieldError> caseManagerFieldError =
      new ArrayList<CaseManagerFieldError>();


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<CaseManagerFieldError> getCaseManagerFieldError() {
    return caseManagerFieldError;
  }

  public void setCaseManagerFieldError(List<CaseManagerFieldError> caseManagerFieldError) {
    this.caseManagerFieldError = caseManagerFieldError;
  }

  public void addFieldError(String fieldName, String errorMessage) {
    CaseManagerFieldError cmFieldErr = new CaseManagerFieldError(fieldName, errorMessage);
    caseManagerFieldError.add(cmFieldErr);
  }

}
