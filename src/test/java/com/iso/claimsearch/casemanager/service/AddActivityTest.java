package com.iso.claimsearch.casemanager.service;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.iso.claimsearch.casemanager.config.CaseManagerApplication;
import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.util.DateUtil;

/**
 * @author i94536
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CaseManagerApplication.class)
@WebAppConfiguration
public class AddActivityTest extends AbstractServiceTest {

  @Autowired
  private ActivityService activityService;

  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void AddActivityTest() throws Exception {
    Activity activity = new Activity();
    Timestamp rightNow = new Timestamp(new Date().getTime());

    activity.setCaseId("223144");
    activity.setUserId("TKSML");
    activity.setActivityDate(rightNow);
    activity.setActivityType(Constants.ACTIVITY_TYP_MOBILE_CODE);
    activity.setActivityCreatedDate(rightNow);
    activity.setTitle("Activity title");
    activity.setDescription("Activity description");
    activity.setActivityDueDate(DateUtil.getFormattedDate(rightNow));
    activity.setActivityStatus(Constants.ACTIVITY_CLOSED_STATUS);
    activity.setActivityEnteredUser("TKSML");
    activity.setActivityAssignedUser("TKSML");

    assertNotNull(activity);
    activityService.addActivity(activity);
  }

}
