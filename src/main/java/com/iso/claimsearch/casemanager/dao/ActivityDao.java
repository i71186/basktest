package com.iso.claimsearch.casemanager.dao;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.model.ActivityAttachment;
import com.iso.claimsearch.casemanager.model.ActivitySynopsis;
import com.iso.claimsearch.casemanager.util.DateUtil;
import com.iso.claimsearch.casemanager.util.StringUtils;



@Repository
public class ActivityDao {

  @Autowired
  @Qualifier("derbyNamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate;

  @Autowired
  @Qualifier("db2NamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;

  private static final String SQL_INSERT_ACTIVITY =
      "INSERT INTO ISO21.V_ACTIVITY (I_ISO_CASE,I_ACT_USR,H_ACT_DT,C_ACT_TYP,H_ACT_CREAT_DT,T_ACT_SUBJ,C_ACT_STUS,	I_ACT_ENTER_USER,I_ACT_ASGN_USER) "
          + "VALUES (:caseId, :userId, :activityDate, :activityType, :activityDate, :title, :status, :userId, :userId)";

  private static final String SQL_INSERT_ACTIVITY_SYNOPSYS =
      "INSERT INTO ISO21.V_ACTIVITY_SYNOP (I_ACT,N_ACT_SYNPS_SEQ,T_ACT_SYNPS) VALUES (:activityId, :sequenceNo, :activityDescription)";

  private static final String SQL_SELECT_ACTIVITY_ID =
      "SELECT I_ACT FROM ISO21.V_ACTIVITY where I_ISO_CASE = :caseId and I_ACT_USR = :userId and C_ACT_STUS = :status and C_ACT_TYP = :activityType "
          + "and I_ACT_ENTER_USER = :userId and H_ACT_DT = :activityDate and H_ACT_CREAT_DT = :activityDate ";

  private static final String SQL_INSERT_ACTIVITY_ATTACH =
      "INSERT INTO ISO21.V_ATTACH (I_ACT,N_ATCH_SEQ,T_FIL_NM,N_FIL_SIZE,I_FIL,T_FILE_PATH,I_FIL_THMB) VALUES (:activityId, :sequenceNo, :fileName, :fileSize, :fileId, :filePath, :thumbFileId)";

  @Transactional
  public Activity addActivity(Activity activity) {

    MapSqlParameterSource parameters = buildActivityQueryParameters(activity);
    int rows = db2NamedParameterJdbcTemplate.update(SQL_INSERT_ACTIVITY, parameters);

    if (rows > 0) {

      MapSqlParameterSource parameter = buildActivityIdQueryParameters(activity);

      Long activityId =
          db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_ACTIVITY_ID, parameter,
              Long.class);

      activity.setActivityId(activityId);

      List synopsis = Arrays.asList(StringUtils.split(activity.getDescription(), 2000));

      SqlParameterSource[] source = new SqlParameterSource[synopsis.size()];
      for (int i = 0; i < synopsis.size(); i++) {
        ActivitySynopsis activitySynopsis = new ActivitySynopsis();

        activitySynopsis.setActivityId(activityId);
        activitySynopsis.setSequenceNo(i);
        activitySynopsis.setActivityDescription(synopsis.get(i).toString());
        source[i] = new BeanPropertySqlParameterSource(activitySynopsis);
      }


      int[] arr = db2NamedParameterJdbcTemplate.batchUpdate(SQL_INSERT_ACTIVITY_SYNOPSYS, source);
    }


    return activity;
  }

  protected MapSqlParameterSource buildActivityQueryParameters(Activity activity) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    activity.setActivityCreatedDate(rightNow);
    activity.setActivityDate(rightNow);
    activity.setActivityDueDate(DateUtil.getFormattedDate(rightNow));
    parameters.addValue("caseId", activity.getCaseId());
    parameters.addValue("userId", activity.getUserId());
    parameters.addValue("activityDate", activity.getActivityDate());
    parameters.addValue("activityType", activity.getActivityType());
    parameters.addValue("title", activity.getTitle());
    parameters.addValue("activityDueDate", activity.getActivityDueDate());
    parameters.addValue("status", activity.getActivityStatus());

    return parameters;
  }

  protected MapSqlParameterSource buildActivityIdQueryParameters(Activity activity) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("caseId", activity.getCaseId());
    parameters.addValue("userId", activity.getUserId());
    parameters.addValue("activityDate", activity.getActivityDate());
    parameters.addValue("activityType", activity.getActivityType());
    parameters.addValue("title", activity.getTitle());
    parameters.addValue("activityDueDate", activity.getActivityDueDate());
    parameters.addValue("status", activity.getActivityStatus());

    return parameters;
  }


  @Transactional
  public int addAttachmentInfo(ActivityAttachment activityAttachment) {

    MapSqlParameterSource parameters = buildAttachmentQueryParameters(activityAttachment);
    int rows = db2NamedParameterJdbcTemplate.update(SQL_INSERT_ACTIVITY_ATTACH, parameters);

    return rows;
  }

  protected MapSqlParameterSource buildAttachmentQueryParameters(
      ActivityAttachment activityAttachment) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("activityId", activityAttachment.getActivityId());
    parameters.addValue("sequenceNo", activityAttachment.getSequenceNo());
    parameters.addValue("fileName", activityAttachment.getFileName());
    parameters.addValue("fileSize", activityAttachment.getFileSize());
    parameters.addValue("fileId", activityAttachment.getFileUid());
    parameters.addValue("filePath", activityAttachment.getFilePath());
    parameters.addValue("thumbFileId", activityAttachment.getThumbFileUid());
    return parameters;
  }

}
