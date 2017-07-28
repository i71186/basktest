package com.iso.claimsearch.casemanager.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author i90845
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CaseManagerApplication.class)
@WebAppConfiguration
public class CaseManagerApplicationTest {

  String args[] = {};

  @Test
  public void contextLoads() {
    CaseManagerApplication.main(args);
  }

}