package com.iso.claimsearch.casemanager.exception;

/**
 * This exception is thrown when Case Manager details are requested when they are not available in
 * ClaimSerach Common database.
 * 
 * @author i90845
 * 
 */
public class CaseManagerNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5450403483522752771L;

  public CaseManagerNotFoundException() {
    super();
  }

  public CaseManagerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CaseManagerNotFoundException(String message) {
    super(message);
  }

  public CaseManagerNotFoundException(Throwable cause) {
    super(cause);
  }
}
