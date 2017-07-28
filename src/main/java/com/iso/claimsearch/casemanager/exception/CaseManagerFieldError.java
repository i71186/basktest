package com.iso.claimsearch.casemanager.exception;

/**
 * This class represents the validation error on a single field while adding an Activity.
 * 
 * @author i90845
 */
public class CaseManagerFieldError {
  private String fieldName;
  private String errorMessage;

  public CaseManagerFieldError(String fieldName, String errorMessage) {
    this.fieldName = fieldName;
    this.errorMessage = errorMessage;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
