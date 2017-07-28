package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

/**
 * Model object for storing the name details
 * 
 * @author i94536
 */

public class Name implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = -4404531954657591445L;
  private String firstName;
  private String middleName;
  private String lastName;
  private String businessName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }
}
