package com.iso.claimsearch.casemanager.model;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Model object for adding an activity
 * 
 * @author i94536
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -1743812495475672061L;

  private Long activityId;

  @NotBlank(message = "{validator.case.manager.addactivity.applicationId}")
  private String appId;

  @NotBlank(message = "{validator.case.manager.addactivity.caseId}")
  private String caseId;
  private String userId;
  private Timestamp activityDate;

  @NotBlank(message = "{validator.case.manager.addactivity.activityType}")
  @Size(min = 4, max = 4, message = "{validator.case.manager.addactivity.activityType.size}")
  private String activityType;
  private Timestamp activityCreatedDate;

  @NotBlank(message = "{validator.case.manager.addactivity.title}")
  @Size(max = 50, message = "{validator.case.manager.addactivity.title.size}")
  private String title;

  @NotBlank(message = "{validator.case.manager.addactivity.description}")
  private String description;

  private Date activityDueDate;
  private String activityStatus;
  private String activityEnteredUser;
  private String activityAssignedUser;

  @AssertTrue(message = "{validator.case.manager.addactivity}")
  public boolean activityRequirement() {
    return isNotBlank(title) || isNotBlank(description);
  }

  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getActivityDate() {
    return activityDate;
  }

  public void setActivityDate(Timestamp activityDate) {
    this.activityDate = activityDate;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }


  public Timestamp getActivityCreatedDate() {
    return activityCreatedDate;
  }

  public void setActivityCreatedDate(Timestamp activityCreatedDate) {
    this.activityCreatedDate = activityCreatedDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getActivityDueDate() {
    return activityDueDate;
  }

  public void setActivityDueDate(Date activityDueDate) {
    this.activityDueDate = activityDueDate;
  }

  public String getActivityStatus() {
    return activityStatus;
  }

  public void setActivityStatus(String activityStatus) {
    this.activityStatus = activityStatus;
  }

  public String getActivityEnteredUser() {
    return activityEnteredUser;
  }

  public void setActivityEnteredUser(String activityEnteredUser) {
    this.activityEnteredUser = activityEnteredUser;
  }

  public String getActivityAssignedUser() {
    return activityAssignedUser;
  }

  public void setActivityAssignedUser(String activityAssignedUser) {
    this.activityAssignedUser = activityAssignedUser;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }
}
