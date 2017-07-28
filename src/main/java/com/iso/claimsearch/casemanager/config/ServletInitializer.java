package com.iso.claimsearch.casemanager.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for running Case Manager service from WAR.
 * 
 * @author i90845
 * 
 */
public class ServletInitializer extends SpringBootServletInitializer {

  // DB2 connection properties
  @Value("${db2.connection.url}")
  private String db2ConnectionURL;

  @Value("${db2.connection.username}")
  private String db2ConnectionUserName;

  @Value("${db2.connection.password}")
  private String db2ConnectionPassword;

  @Value("${db2.connection.driverClassName}")
  private String db2ConnectionDriverClassName;

  @Value("${db2.connection.jndiName}")
  private String db2ConnectionJndiName;

  @Value("${db2.spring.datasource.jndi-name}")
  private String db2JndiLookupName;


  // Derby database connection properties
  @Value("${derby.connection.url}")
  private String derbyConnectionURL;

  @Value("${derby.connection.driverClassName}")
  private String derbyConnectionDriverClassName;

  @Value("${derby.connection.jndiName}")
  private String derbyConnectionJndiName;

  @Value("${derby.connection.initial-size}")
  private String derbyConnectionPoolInitialSize;

  @Value("${derby.connection.max-active}")
  private String derbyConectionPoolMaxActive;

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate;
  }

  /**
   * Derby Embedded JDBC template
   * 
   * @param sqlServerDataSource
   * @return namedParameterJdbcTemplate
   */
  @Bean(name = "derbyNamedParameterJdbcTemplate")
  @ConfigurationProperties(prefix = "datasource.primary")
  NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate(
      @Qualifier("derbyJndiDataSource") DataSource db2JndiDataSource) {
    NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(db2JndiDataSource);
    return derbyNamedParameterJdbcTemplate;
  }


  /**
   * DB2 JDBC template
   * 
   * @param sqlServerDataSource
   * @return namedParameterJdbcTemplate
   */
  @Bean(name = "db2NamedParameterJdbcTemplate")
  @ConfigurationProperties(prefix = "datasource.secondary")
  NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate(
      @Qualifier("db2JndiDataSource") DataSource db2JndiDataSource) {
    NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(db2JndiDataSource);
    return db2NamedParameterJdbcTemplate;
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(CSOAuthConfiguration.class, CaseManagerApplication.class);
  }

  @Bean
  public TomcatEmbeddedServletContainerFactory tomcatFactory() {
    return new TomcatEmbeddedServletContainerFactory() {
      @Override
      protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
        tomcat.enableNaming();
        return super.getTomcatEmbeddedServletContainer(tomcat);
      }

      @Override
      protected void postProcessContext(Context context) {
        buildDerbyResource(context);
        buildDB2Resoruce(context);
        // buildSqlServerResource(context);
      }

      protected void buildDerbyResource(Context context) {
        ContextResource derbyResource = new ContextResource();
        derbyResource.setName(derbyConnectionJndiName);
        derbyResource.setType(DataSource.class.getName());
        derbyResource.setProperty("driverClassName", derbyConnectionDriverClassName);
        derbyResource.setProperty("url", derbyConnectionURL);
        derbyResource.setProperty("initial-size", derbyConnectionPoolInitialSize);
        derbyResource.setProperty("max-active", derbyConectionPoolMaxActive);
        context.getNamingResources().addResource(derbyResource);
      }

      protected void buildDB2Resoruce(Context context) {
        ContextResource db2Resource = new ContextResource();
        db2Resource.setName(db2ConnectionJndiName);
        db2Resource.setType(DataSource.class.getName());
        db2Resource.setProperty("driverClassName", db2ConnectionDriverClassName);
        db2Resource.setProperty("url", db2ConnectionURL);
        db2Resource.setProperty("password", db2ConnectionPassword);
        db2Resource.setProperty("username", db2ConnectionUserName);
        context.getNamingResources().addResource(db2Resource);
      }

    };
  }

  /**
   * Data Source for Derby embedded Database.
   * 
   * @return derbyBean
   * @throws NamingException
   */
  @Bean(name = "derbyJndiDataSource")
  @Primary
  @ConfigurationProperties(prefix = "datasource.primary")
  public DataSource derbyJndiDataSource() throws NamingException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(derbyConnectionDriverClassName);
    dataSource.setUrl(derbyConnectionURL);
    return dataSource;
  }

  /**
   * Data Soruce for DB2 Database.
   * 
   * @return db2Bean
   * @throws NamingException
   */
  @Bean(name = "db2JndiDataSource", destroyMethod = "")
  @ConfigurationProperties(prefix = "datasource.secondary")
  public DataSource db2JndiDataSource() throws NamingException {
    JndiObjectFactoryBean db2Bean = new JndiObjectFactoryBean();
    db2Bean.setJndiName(db2JndiLookupName);
    db2Bean.setProxyInterface(DataSource.class);
    db2Bean.setLookupOnStartup(false);
    db2Bean.afterPropertiesSet();
    return (DataSource) db2Bean.getObject();
  }
}
