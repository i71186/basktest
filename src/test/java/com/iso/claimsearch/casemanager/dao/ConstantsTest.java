package com.iso.claimsearch.casemanager.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConstantsTest {

  public static final String DASHBOARDS_PRODUCT_CODE = "ACCOUNT_MANAGEMENT";

  public static final String OPEN_CASE_STATUS = "0001";

  public static final String SUCCESS = "Success";

  public static final String NO_OPEN_CASES = "No Open Cases";

  public static final String COMPANY_ID = "iso";

  public static final String ROLE_TYP = "C_ENTITY_ROLE";

  public static final String INSURED_ROLE = "0022";

  public static final String ROLE_CASE_MANAGER_USER = "ROLE_CASE_MANAGER_USER";

  public static final String NO_CASE_ENTITIES = "No Case Entities";

  public static final String ACCESS_DENIED = "access_denied";

  public static final String ACTIVITY_CLOSED_STATUS = "0001";

  public static final String ACTIVITY_TYP_MOBILE_CODE = "0107";

  public static final String FAILED = "No Records Inserted";

  @Test
  public void testCaseManagerConstants() {
    assertEquals(OPEN_CASE_STATUS, Constants.OPEN_CASE_STATUS);

    assertEquals(SUCCESS, Constants.SUCCESS);

    assertEquals(NO_OPEN_CASES, Constants.NO_OPEN_CASES);

    assertEquals(COMPANY_ID, Constants.COMPANY_ID);

    assertEquals(ROLE_TYP, Constants.ROLE_TYP);

    assertEquals(INSURED_ROLE, Constants.INSURED_ROLE);

    assertEquals(ROLE_CASE_MANAGER_USER, Constants.ROLE_CASE_MANAGER_USER);

    assertEquals(NO_CASE_ENTITIES, Constants.NO_CASE_ENTITIES);

    assertEquals(ACCESS_DENIED, Constants.ACCESS_DENIED);

    assertEquals(ACTIVITY_CLOSED_STATUS, Constants.ACTIVITY_CLOSED_STATUS);

    assertEquals(ACTIVITY_TYP_MOBILE_CODE, Constants.ACTIVITY_TYP_MOBILE_CODE);

    assertEquals(FAILED, Constants.FAILED);
  }

}
