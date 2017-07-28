package com.iso.claimsearch.casemanager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.iso.claimsearch.casemanager.model.CaseDetails;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.Name;


/**
 * Data Repository for Case Manager service.
 * 
 * @author i90845
 */
@Repository
public class CaseDao {

  private MyCasesRowMapper myCasesRowMapperer = new MyCasesRowMapper();
  private CaseEntitiesRowMapper caseEntitiesRowMapper = new CaseEntitiesRowMapper();
  private CaseDetailRowMapper caseDetailsRowMapper = new CaseDetailRowMapper();
  private CaseInsuredRowMapper caseInsuredRowMapper = new CaseInsuredRowMapper();


  @Autowired
  @Qualifier("derbyNamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate;

  @Autowired
  @Qualifier("db2NamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;


  private static final String SQL_SELECT_MYCASES =
      "SELECT I_ISO_CASE, N_CASE, M_CITY, C_ST_ALPH, T_CASE_DSC, H_REC_MODF FROM ISO21.V_CASE WHERE I_ISO_CASE IN (SELECT I_ISO_CASE FROM ISO21.V_CASE_SUPPORT "
          + "WHERE I_USR = :userId and C_CASE_STUS = :status) ORDER BY N_CASE ASC";


  private static final String SQL_SELECT_CASE_ENTITIES =
      "SELECT I_CASE_ENTY, "
          + "(SELECT T_CD_DSC FROM ISO21.V_UNIV_SIUCM where I_CUST = :companyId "
          + "and M_CD_COLM = :roleType and C_CD_VAL = CE.C_ROLE ) as ROLE, "
          + "H_REC_MODF, M_BUS_NM, M_FST, M_MID, M_LST, T_ADR_LN1, T_ADR_LN2, M_CITY, C_ST_ALPH, C_ZIP, C_CNTRY "
          + "FROM ISO21.V_CASE_ENTITY CE where I_ISO_CASE = :caseId order by ROLE ";

  private static final String SQL_CASE_DET =
      "SELECT I_ISO_CASE, N_CASE, H_REC_MODF, M_CITY, C_ST_ALPH FROM ISO21.V_CASE where I_ISO_CASE = :caseId";


  private static final String SQL_CASE_INSURED =
      "SELECT M_BUS_NM, M_FST, M_MID, M_LST FROM ISO21.V_CASE_ENTITY where I_ISO_CASE = :caseId and C_ROLE = :insuredRole order by D_ORIG ASC fetch first row only";



  public List<MyCase> getMyOpenCases(String userId, String status) {

    Map<String, String> queryParameters = buildMyOpenCasesQueryParameters(userId, status);

    List<MyCase> myOpenCases =
        (List<MyCase>) db2NamedParameterJdbcTemplate.query(SQL_SELECT_MYCASES, queryParameters,
            myCasesRowMapperer);

    for (MyCase myCase : myOpenCases) {

      myCase.setInsured(getCasePrimaryInsured(myCase.getCaseId().toString()));
    }

    return myOpenCases;
  }

  public List<CaseEntity> getCaseEntities(String caseId, String companyId) {

    Map<String, String> queryParameters = buildCaseEntitiesQueryParameters(caseId, companyId);

    List<CaseEntity> caseEntities =
        (List<CaseEntity>) db2NamedParameterJdbcTemplate.query(SQL_SELECT_CASE_ENTITIES,
            queryParameters, caseEntitiesRowMapper);

    return caseEntities;
  }


  public Name getCasePrimaryInsured(String caseId) {
    Name insuredName = new Name();
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("caseId", caseId);
    queryParameters.put("insuredRole", Constants.INSURED_ROLE);

    List<Name> insuredNames =
        (List<Name>) db2NamedParameterJdbcTemplate.query(SQL_CASE_INSURED, queryParameters,
            caseInsuredRowMapper);

    if (insuredNames != null && insuredNames.size() > 0) {
      insuredName = insuredNames.get(0);
    }

    return insuredName;
  }


  public CaseDetails getCaseDetails(String caseId) {

    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("caseId", caseId);

    CaseDetails caseDetails =
        (CaseDetails) db2NamedParameterJdbcTemplate.queryForObject(SQL_CASE_DET, queryParameters,
            caseDetailsRowMapper);

    return caseDetails;
  }

  protected Map<String, String> buildMyOpenCasesQueryParameters(String userId, String status) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("userId", userId);
    queryParameters.put("status", status);

    return queryParameters;
  }

  protected Map<String, String> buildCaseEntitiesQueryParameters(String caseId, String companyId) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("caseId", caseId);
    queryParameters.put("companyId", companyId);
    queryParameters.put("roleType", Constants.ROLE_TYP);
    return queryParameters;
  }

}
