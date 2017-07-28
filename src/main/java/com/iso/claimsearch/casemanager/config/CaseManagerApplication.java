package com.iso.claimsearch.casemanager.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This is the main class to launch Case Manager Service.
 * 
 * @author i90845
 * 
 */
@Configuration
@SpringBootApplication
@ComponentScan("demoboot,com.iso.claimsearch")
@EnableAutoConfiguration
@EnableTransactionManagement
public class CaseManagerApplication extends ServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(CaseManagerApplication.class, args);
  }
}
