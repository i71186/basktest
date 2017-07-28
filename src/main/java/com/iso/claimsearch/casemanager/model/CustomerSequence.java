package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

public class CustomerSequence implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8722000750214394198L;
  private String companyId;
  private int sequenceNumber;
  private String sequenceGroup;

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getSequenceGroup() {
    return sequenceGroup;
  }

  public void setSequenceGroup(String sequenceGroup) {
    this.sequenceGroup = sequenceGroup;
  }

  public int getSequenceNumber() {
    return sequenceNumber;
  }

  public void setSequenceNumber(int sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

}
