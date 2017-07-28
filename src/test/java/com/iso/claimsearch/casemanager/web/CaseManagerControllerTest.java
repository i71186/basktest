package com.iso.claimsearch.casemanager.web;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.iso.claimsearch.casemanager.config.CaseManagerApplication;
import com.iso.claimsearch.casemanager.dao.CaseDao;
import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.CaseEntityResponse;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.MyCasesResponse;
import com.iso.claimsearch.casemanager.service.CaseEntityService;
import com.iso.claimsearch.casemanager.service.CaseService;


/**
 * @author i90845
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CaseManagerApplication.class)
@WebAppConfiguration
public class CaseManagerControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mvc;

  @Mock
  private CaseService caseService;

  @Mock
  private CaseEntityService caseEntityService;

  @Mock
  private CaseDao caseDao;

  @Mock
  private MyCasesResponse myCasesResponse;

  @Mock
  private CaseEntityResponse caseEntityResponse;

  @InjectMocks
  private CaseManagerController caseManagerController;

  private String userId = "TKSMQ";
  private String companyId = "iso";
  private String caseId = "323143";

  @Mock
  List<MyCase> myOpenCases;

  @Mock
  List<CaseEntity> caseEntities;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(caseManagerController).build();
    myOpenCases = new ArrayList<MyCase>();
    caseEntities = new ArrayList<CaseEntity>();
    myCasesResponse = buildMyCasesResponse();
    ReflectionTestUtils.setField(caseManagerController, "caseService", caseService);
    ReflectionTestUtils.setField(caseManagerController, "caseEntityService", caseEntityService);
  }

  /*
   * @Test public void testGetMyCases() throws Exception {
   * Mockito.when(caseService.getMyOpenCases(userId)).thenReturn(myOpenCases);
   * 
   * try { mvc.perform( MockMvcRequestBuilders.get("/1.0/mycases", "TKSMQ").accept(
   * MediaType.APPLICATION_JSON)).andExpect( status().isOk()); } catch (Exception e) {
   * e.printStackTrace(); }
   * 
   * verify(caseService).getMyOpenCases(userId); }
   */

  private MyCasesResponse buildMyCasesResponse() {
    MyCasesResponse response = new MyCasesResponse();
    response.setStatus(Constants.SUCCESS);
    return response;
  }

  @Test
  public void testGetCaseEntities() throws Exception {
    Mockito.when(caseEntityService.getCaseEntities(caseId, companyId)).thenReturn(caseEntities);

    try {
      mvc.perform(
          MockMvcRequestBuilders.get("/1.0/caseentities/{caseId}", "323143").accept(
              MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // verify(caseEntityService).getCaseEntities(caseId, companyId);
  }
}
