package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class represents Case Entity in Claim Search Case Manager Case.
 * 
 * @author i94536
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseEntity implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = -5471842006200661799L;
  private String caseRole;
  private String lastUpdated;
  private Name name;
  private Address address;

  public String getCaseRole() {
    return caseRole;
  }

  public void setCaseRole(String caseRole) {
    this.caseRole = caseRole;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }


}
