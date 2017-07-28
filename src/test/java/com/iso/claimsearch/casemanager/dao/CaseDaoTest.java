package com.iso.claimsearch.casemanager.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import com.iso.claimsearch.casemanager.config.CaseManagerApplication;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.service.AbstractServiceTest;

/**
 * @author i90845
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CaseManagerApplication.class)
@WebAppConfiguration
@PropertySource("classpath:application.properties")
public class CaseDaoTest extends AbstractServiceTest {

  @Value("${derby.spring.datasource.jndi-name}")
  private String jndiLookupName;

  @Autowired
  private CaseDao caseDao;

  @Autowired
  @Qualifier("derbyNamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  public void testGetMyOpenCases() throws NamingException {
    List<MyCase> myOpenCases = caseDao.getMyOpenCases("TKSMQ", ConstantsTest.OPEN_CASE_STATUS);
    assertNotNull(myOpenCases);
  }

  @Test
  public void testGetCaseEntities() throws NamingException {
    List<CaseEntity> myCaseEntities = caseDao.getCaseEntities("323143", "N010");
    assertNotNull(myCaseEntities);
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }
}
