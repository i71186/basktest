package com.iso.claimsearch.casemanager.service;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.iso.claimsearch.casemanager.config.CaseManagerApplication;
import com.iso.claimsearch.casemanager.dao.CaseDao;
import com.iso.claimsearch.casemanager.dao.JndiDatasourceCreator;
import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.model.ActivityResponse;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.CaseEntityResponse;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.MyCasesResponse;

/**
 * @author i90845
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CaseManagerApplication.class)
@WebAppConfiguration
@PropertySource("classpath:application.properties")
public abstract class AbstractServiceTest {

  @Value("${db2.spring.datasource.jndi-name}")
  private String db2JndiLookupName;

  @Autowired
  @Qualifier("db2NamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;

  @Value("${derby.spring.datasource.jndi-name}")
  private String jndiLookupName;

  @Autowired
  @Qualifier("derbyNamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate;

  @Autowired
  protected CaseDao caseDao;

  @Autowired
  protected CaseService caseService;

  @Autowired
  protected CaseEntityService caseEntityService;

  @Autowired
  protected ActivityService activityService;

  @Autowired
  private RestTemplate restTemplate;

  private Activity activity;
  private List<MyCase> myOpenCases;
  private List<CaseEntity> myCaseEntities;
  private MyCasesResponse myCasesResponse;
  private CaseEntityResponse caseEntityResponse;
  private ActivityResponse activityResponse;

  private String userId = "TKSMQ";
  private String caseId = "323143";
  private String companyId = "N010";

  public static final String INSERT_CASE =
      "INSERT INTO ISO21.V_CASE (I_ISO_CASE,I_CUST,N_CASE,I_REFR_TYP,I_LEAD_INVST_USER,C_CASE_STUS,D_ASGN,D_CLOSE,M_CITY,C_ST_ALPH,M_REFR_CNTCT,N_REFR_CNTCT_TEL,T_CASE_DSC,C_PRIR,C_INVST_TYP,C_INVST_SUB_TYP,N_TM_SVC,C_DISP,A_ECNMC_IMP,C_INVST_LOC_ZIP,D_STTL,C_CASE_TYP,I_RPT_TO_USR,C_CO_DPT,C_CO_BUS_UNIT,C_CO_OFC,C_COUNTY_SIUC,F_SNTV_CASE,H_REC_MODF,I_USER_MODF,D_FOLLOW_UP,H_LST_ACT) VALUES (323143,'N010','C0345678-61','0001','TKSMQ','0001',{d '2015-05-06'},null,'schamburg','IL','Manager,A,1','2015551234','','    ','0003','0010',null,null,null,'',null,'0003','KZYLY','0002','0005','0001','0714','N',{ts '2015-10-29 15:03:01'},'TKSMQ',null,{ts '2015-11-03 16:08:38'})";
  public static final String INSERT_CASE_SUPPORT =
      "INSERT INTO ISO21.V_CASE_SUPPORT (I_ISO_CASE,I_USR,C_CASE_STUS,C_RLE_CASE_USR) VALUES (323143,'TKSMQ','0001','0001')";

  public static final String INSERT_CASE_ENTITIES =
      "INSERT INTO ISO21.V_CASE_ENTITY (I_ISO_CASE,N_CLM_UND_ASST_SEQ,I_CASE_ENTY,I_CUST,D_ORIG,C_ENTY_TYP,I_PRNT_CASE_ENTY,M_BUS_NM,C_ROLE,M_FST,M_MID,M_LST,T_ADR_LN1,T_ADR_LN2,M_CITY,C_ST_ALPH,C_ZIP,C_CNTRY,D_BRTH,N_SSN,N_LIC_PLT,C_ST_LIC_PLT,N_PSPRT,N_TIN,F_SRCH,C_GEND,T_OCCP,N_PROF_MED_LIC,N_DRV_LIC,C_ST_DRV_LIC,F_SUBJ_INVST,M_EMPR,M_CMT,T_ENTY_SRCE,N_VIN,H_REC_MODF,I_USER_MODF) VALUES (323143,1,662903,'N010',{ts '2015-10-21 16:02:49'},'0001',null,'','0022','CAROM','LESLIE','MAYHAND-BRISUENP','4 LINEAGE CT','','NOTTINGHAM','MD  ','212362819','US  ',{d '2015-10-21'},'         ','5BP6543','MD  ','','         ','N','    ','','','M531108511835','MD  ','N','','','Initial Referral','6NPDH4AE3FH589778',{ts '2016-03-02 17:53:47'},'TKSMQ')";
  public static final String INSERT_CE_CODELIST =
      "INSERT INTO ISO21.V_UNIV_SIUCM (I_CUST,M_CD_COLM,C_CD_VAL,M_PRNT_CD_COLM,C_PRNT_CD_VAL,T_CD_DSC) VALUES ('N010','C_ENTITY_ROLE','0001','','    ','1st party claimant')";

  public static final String DELETE_CASE_SUPPORT =
      "DELETE FROM ISO21.V_CASE_SUPPORT where I_ISO_CASE = 323143";
  public static final String DELETE_CASE = "DELETE FROM ISO21.V_CASE where I_ISO_CASE = 323143";

  public static final String DELETE_CE_CODELIST =
      "DELETE FROM ISO21.V_UNIV_SIUCM where I_CUST = 'N010' and M_CD_COLM = 'C_ENTITY_ROLE' and C_CD_VAL = '0001'";
  public static final String DELETE_CASE_ENTITIES =
      "DELETE FROM ISO21.V_CASE_ENTITY where I_ISO_CASE = 323143";

  public static final String INSERT_ACTIVITY =
      "INSERT INTO ISO21.V_ACTIVITY (I_ISO_CASE,I_ACT_USR,H_ACT_DT,C_ACT_TYP,H_ACT_CREAT_DT,T_ACT_SUBJ,H_ACT_DUE_DT,C_ACT_STUS,I_ACT_ENTER_USER,I_ACT_ASGN_USER) VALUES (324321,'TKSMQ','2016-01-06 15:20:22.017','0107','2016-01-06 15:20:22.017', 'Activity title', '2016-01-06 15:20:22.017', '0001','TKSMQ','TKSMQ')";
  public static final String INSERT_ACTIVITY_SYNOPSYS =
      "INSERT INTO ISO21.V_ACTIVITY_SYNOP (I_ACT,N_ACT_SYNPS_SEQ,T_ACT_SYNPS) VALUES (1061234,0,'Approved')";

  public static final String DELETE_INSERT_ACTIVITY =
      "DELETE FROM ISO21.V_ACTIVITY where  I_ISO_CASE = 324321 and I_ACT_USR = 'TKSMQ' and H_ACT_DT = '2016-01-06 15:20:22.017' and C_ACT_TYP = '0107' and H_ACT_CREAT_DT = '2016-01-06 15:20:22.017' and T_ACT_SUBJ = '' and H_ACT_DUE_DT = '2016-01-06 15:20:22.017' and C_ACT_STUS = '0001' and I_ACT_ENTER_USER = 'TKSMQ' and I_ACT_ASGN_USER = 'TKSMQ' ";
  public static final String DELETE_ACTIVITY_SYNOPSYS =
      "DELETE FROM ISO21.V_ACTIVITY_SYNOP where I_ACT = 1061234 and N_ACT_SYNPS_SEQ = 0 and T_ACT_SYNPS = 'Approved'";

  @Before
  public void setUp() throws Exception {
    JndiDatasourceCreator.createDb2DataSource();

    executeInsertDB2Sql(INSERT_CASE);
    executeInsertDB2Sql(INSERT_CASE_SUPPORT);
    executeInsertDB2Sql(INSERT_CASE_ENTITIES);
    executeInsertDB2Sql(INSERT_CE_CODELIST);
    // executeInsertDB2Sql(INSERT_ACTIVITY);
    // executeInsertDB2Sql(INSERT_ACTIVITY_SYNOPSYS);

    restTemplate = new RestTemplate(getClientConnectionFactory());
    myOpenCases = caseService.getMyOpenCases(userId);
    myCasesResponse = new MyCasesResponse();

    myCaseEntities = caseEntityService.getCaseEntities(caseId, companyId);
    caseEntityResponse = new CaseEntityResponse();

    // activity = activityService.addActivity(activity);
    activityResponse = new ActivityResponse();
  }

  @After
  public void tearDown() throws Exception {
    executeDeleteDB2Sql(DELETE_CASE_SUPPORT);
    executeDeleteDB2Sql(DELETE_CASE);
    executeDeleteDB2Sql(DELETE_CASE_ENTITIES);
    executeDeleteDB2Sql(DELETE_CE_CODELIST);
    // executeDeleteDB2Sql(DELETE_INSERT_ACTIVITY);
    // executeDeleteDB2Sql(DELETE_ACTIVITY_SYNOPSYS);

  }

  protected void executeInsertDB2Sql(String sql) {
    db2NamedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
  }

  protected void executeUpdateDB2Sql(String sql) {
    db2NamedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
  }

  protected void executeDeleteDB2Sql(String sql) {
    db2NamedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
  }


  private ClientHttpRequestFactory getClientConnectionFactory() throws GeneralSecurityException {
    SSLContext sslcontext =
        SSLContexts.custom().useTLS().loadTrustMaterial(null, new TrustStrategy() {
          @Override
          public boolean isTrusted(X509Certificate[] chain, String authType)
              throws CertificateException {
            return true;
          }
        }).build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();

    SSLConnectionSocketFactory sf =
        new SSLConnectionSocketFactory(sslcontext,
            SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sf).build();
    requestFactory.setHttpClient(httpclient);
    return requestFactory;
  }


  public RestTemplate getRestTemplate() {
    return restTemplate;
  }

  public CaseService getCaseService() {
    return caseService;
  }

  public List<MyCase> getMyOpenCases() {
    return myOpenCases;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public ActivityResponse getActivityResponse() {
    return activityResponse;
  }

  public void setActivityResponse(ActivityResponse activityResponse) {
    this.activityResponse = activityResponse;
  }

  public MyCasesResponse getMyCasesResponse() {
    return myCasesResponse;
  }

  public CaseEntityResponse getCaseEntityResponse() {
    return caseEntityResponse;
  }

  public void setCaseEntityResponse(CaseEntityResponse caseEntityResponse) {
    this.caseEntityResponse = caseEntityResponse;
  }

  public List<CaseEntity> getMyCaseEntities() {
    return myCaseEntities;
  }

  public void setMyCaseEntities(List<CaseEntity> myCaseEntities) {
    this.myCaseEntities = myCaseEntities;
  }


  public ActivityService getActivityService() {
    return activityService;
  }

  public void setActivityService(ActivityService activityService) {
    this.activityService = activityService;
  }


}
