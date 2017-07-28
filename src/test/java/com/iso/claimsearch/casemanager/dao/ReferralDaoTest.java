package com.iso.claimsearch.casemanager.dao;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class ReferralDaoTest {


  @Autowired
  ReferralDao referralDao;

  @Autowired
  @Qualifier("db2NamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Before
  public void setUp() throws Exception {
    executeSqlScript("sql/create-schema.sql");
    executeSqlScript("sql/create-Tables.sql");
  }

  @After
  public void tearDown() throws Exception {
    executeSqlScript("sql/drop-schema.sql");
  }

  @Test
  public void testGenerateReferralNumberFromCompanySequence() {
    executeSql("INSERT INTO ISO21.V_CUST_REFR_SEQ (I_CUST,T_REFR_GRP,N_SEQ_TRK) VALUES ('S138','2017',10)");
    String referralNumber = referralDao.generateReferralNumberFromCustomerSequence("S138");
    Assert.assertEquals("2017000011", referralNumber);
  }

  @Test
  public void testGenerateReferralNumberFromCompanySequenceNonCurrentYear() {
    executeSql("INSERT INTO ISO21.V_CUST_REFR_SEQ (I_CUST,T_REFR_GRP,N_SEQ_TRK) VALUES ('S138','2015',10)");
    String referralNumber = referralDao.generateReferralNumberFromCustomerSequence("S138");
    Assert.assertEquals("2017000001", referralNumber);
  }

  @SuppressWarnings("deprecation")
  private void executeSqlScript(String fileClassPath) {
    JdbcTestUtils.executeSqlScript(jdbcTemplate, new ClassPathResource(fileClassPath), false);
  }

  private void executeSql(String sql) {
    namedParameterJdbcTemplate.update(sql, new HashMap<String, Object>());
  }
}
