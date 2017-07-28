package com.iso.claimsearch.casemanager.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.exception.CaseManagerNotFoundException;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.CaseEntityResponse;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.MyCasesResponse;

/**
 * @author i90845
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CaseServiceTest extends AbstractServiceTest {

  private List<MyCase> myOpenCases;
  private MyCasesResponse myCasesResponse;
  private List<CaseEntity> myCaseEntities;
  private CaseEntityResponse caseEntityResponse;
  private String userId = "TKSMQ";
  private String caseId = "323143";


  @Before
  public void setUp() throws Exception {
    super.setUp();
    caseService.getMyOpenCases(userId);
    myCasesResponse = new MyCasesResponse();
    caseEntityService.getCaseEntities(caseId, Constants.COMPANY_ID);
    caseEntityResponse = new CaseEntityResponse();
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testGetMyOpenCases() throws Exception {

    myOpenCases = caseService.getMyOpenCases(userId);
    assertTrue(myOpenCases.size() > 0);

  }

  @Test
  public void testGetCaseEntities() throws Exception {

    myCaseEntities = caseEntityService.getCaseEntities(caseId, Constants.COMPANY_ID);
    assertTrue(myCaseEntities.size() > 0);

  }

  @Test
  public void testMyOpenCases() {

    myOpenCases = caseDao.getMyOpenCases(userId, Constants.OPEN_CASE_STATUS);
    assertTrue(myOpenCases.size() > 0);

  }

  @Test
  public void testCaseEntity() {

    myCaseEntities = caseDao.getCaseEntities(caseId, Constants.COMPANY_ID);
    assertTrue(myCaseEntities.size() > 0);

  }



}
