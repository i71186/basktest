package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

/**
 * Model object for activity synopsis
 * 
 * @author i90845
 */
public class ActivitySynopsis implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -3070234282373865062L;

  private Long activityId;

  private int sequenceNo;

  private String activityDescription;


  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public int getSequenceNo() {
    return sequenceNo;
  }

  public void setSequenceNo(int sequenceNo) {
    this.sequenceNo = sequenceNo;
  }

  public String getActivityDescription() {
    return activityDescription;
  }

  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }


}
