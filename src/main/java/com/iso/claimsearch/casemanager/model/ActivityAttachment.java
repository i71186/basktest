package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;

public class ActivityAttachment implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -9219873975931724485L;

  private long activityId;

  private long sequenceNo;

  private String fileName;

  private long fileSize;

  private long fileUid;

  private String filePath;

  private long thumbFileUid = 0;


  public long getActivityId() {
    return activityId;
  }

  public void setActivityId(long activityId) {
    this.activityId = activityId;
  }

  public long getSequenceNo() {
    return sequenceNo;
  }

  public void setSequenceNo(long sequenceNo) {
    this.sequenceNo = sequenceNo;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  public long getFileUid() {
    return fileUid;
  }

  public void setFileUid(long fileUid) {
    this.fileUid = fileUid;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public long getThumbFileUid() {
    return thumbFileUid;
  }

  public void setThumbFileUid(long thumbFileUid) {
    this.thumbFileUid = thumbFileUid;
  }



}
