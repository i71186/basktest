package com.iso.claimsearch.casemanager.dao;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * 
 * @author i90845
 * 
 */
public class JndiDatasourceCreator {

  private static final Logger LOGGER = Logger.getLogger(JndiDatasourceCreator.class);

  // DB2
  private static final String db2username = "PIDDEMT";
  private static final String db2Password = "agt99995";
  private static final String db2JndiName = "CSCaseManagerSIUCDataSource";
  private static final String db2DriverClassName = "com.ibm.db2.jcc.DB2Driver";
  private static final String db2Url = "jdbc:db2://isoc.iso.com:2448/ISONT";
  public static BasicDataSource db2SataSource;

  // Derby
  private static final String derbyJndiName = "CSCaseManagerSIUCDerbyDataSource";
  private static final String derbyURL = "jdbc:derby:memory:claimsinquirytestdb;create=true";
  private static final String derbyDriverClassName = "org.apache.derby.jdbc.EmbeddedDriver";
  public static BasicDataSource derbyDataSource;

  /** An instance of this class bound to JNDI */
  private static volatile SimpleNamingContextBuilder builder;

  /**
   * Create and activate JNDI DataSoruce to Derby
   * 
   * @throws Exception
   */
  public static void createDerbyDataSoruce() throws Exception {
    try {
      builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
      derbyDataSource = new BasicDataSource();
      derbyDataSource.setDriverClassName(derbyDriverClassName);
      derbyDataSource.setUrl(derbyURL);
      builder.bind("java:comp/env/jdbc/" + derbyJndiName, derbyDataSource);
      builder.activate();
    } catch (NamingException ex) {
      LOGGER.info(ex.getMessage());
    }
  }

  /**
   * Create and activate JNDI DataSoruce to DB2
   * 
   * @throws Exception
   */
  public static void createDb2DataSource() throws Exception {
    try {
      builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
      db2SataSource = new BasicDataSource();
      db2SataSource.setUsername(db2username);
      db2SataSource.setPassword(db2Password);
      db2SataSource.setDriverClassName(db2DriverClassName);
      db2SataSource.setUrl(db2Url);
      builder.bind("java:comp/env/jdbc/" + db2JndiName, db2SataSource);
      builder.activate();
    } catch (NamingException ex) {
      LOGGER.info(ex.getMessage());
    }
  }
}
