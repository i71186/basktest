package com.iso.claimsearch.casemanager.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.model.ClaimReferral;
import com.iso.claimsearch.casemanager.model.CustomerSequence;
import com.iso.claimsearch.casemanager.model.Phone;
import com.iso.claimsearch.casemanager.model.Referral;
import com.iso.claimsearch.casemanager.model.ReferralEntity;

/**
 * Data Repository for Create Referral service.
 * 
 * 
 */

@Repository
public class ReferralDao {
  private static final Logger LOGGER = Logger.getLogger(ReferralDao.class);

  private ReferralRowMapper referralRowMapper = new ReferralRowMapper();
  private ReferralEntitiesRowMapper referralEntitiesRowMapper = new ReferralEntitiesRowMapper();
  private ClaimRowMapper claimRowMapper = new ClaimRowMapper();
  private CustomerSequenceRowMapper customerSequenceRowMapper = new CustomerSequenceRowMapper();

  @Autowired
  @Qualifier("derbyNamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate derbyNamedParameterJdbcTemplate;

  @Autowired
  @Qualifier("db2NamedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate;

  private static final String SQL_INSERT_REFERRAL =
      "INSERT INTO ISO21.V_REFER (I_CUST, N_REFR, I_USER, I_USER_MGR, D_ASGN,  D_RCV, M_CITY, C_ST_ALPH, I_ALLCLM, C_CO_BUS_UNIT, N_POL, C_REF_STUS, I_USR_CREAT, N_CLM, D_OCUR, C_LOSS_TYP, C_LOSS_SUB_TYP, M_LOL_ADR_LN1, M_LOL_CITY, C_LOL_ST_ALPH, I_REFR_TYP, C_RSN_SUSP) "
          + "VALUES(:companyId, :referralNumber, :source, :managerQueue, :dateAssigned, :dateReceived, :city, :state, :allclmId , :businessUnit, :policyNumber, :referralStatus, :createdBy, :claimNumber, :dateOfLoss, :lossType, :lossSubType, :lolAddress, :lolCity, :lolState, :referralType, :suspicionReason)";

  private static final String SQL_INSERT_REFERRAL_SCIF =
      "INSERT INTO ISO21.V_REFER (I_CUST, N_REFR, I_USER, I_USER_MGR, D_ASGN,  D_RCV, M_CITY, C_ST_ALPH, I_ALLCLM, C_CO_BUS_UNIT, N_POL, C_REF_STUS, I_USR_CREAT, N_CLM, D_OCUR, C_LOSS_TYP, C_LOSS_SUB_TYP, M_LOL_ADR_LN1, M_LOL_CITY, C_LOL_ST_ALPH, I_REFR_TYP, C_RSN_SUSP, M_AGT, I_EMAIL) "
          + "VALUES(:companyId, :referralNumber, :source, :managerQueue, :dateAssigned, :dateReceived, :city, :state, :allclmId , :businessUnit, :policyNumber, :referralStatus, :createdBy, :claimNumber, :dateOfLoss, :lossType, :lossSubType, :lolAddress, :lolCity, :lolState, :referralType, :suspicionReason, :agentName, :agentEmail)";

  private static final String SQL_INSERT_REFERRAL_ENTITY =
      "INSERT INTO ISO21.V_REFER_ENTITY (I_ISO_REFR, I_CUST, D_ORIG, C_ENTY_TYP, I_PRNT_CASE_ENTY, M_BUS_NM,"
          + "C_ROLE, M_FST, M_MID, M_LST, T_ADR_LN1, T_ADR_LN2, M_CITY, C_ST_ALPH, C_ZIP, C_CNTRY,"
          + " D_BRTH, N_SSN, N_LIC_PLT, C_ST_LIC_PLT, N_PSPRT, N_TIN, F_SRCH, C_GEND, T_OCCP, N_PROF_MED_LIC, N_DRV_LIC, C_ST_DRV_LIC, F_SUBJ_INVST, M_CMT, T_ENTY_SRCE, N_VIN)"
          + " VALUES (:referralId, :companyId, :dateReceived, :type, :parentEntity, :businessName, "
          + " :role, :firstName, :middleName, :lastName, :address1, :address2, :city, :state, :zipCode, :country , "
          + " :dateOfBirth, :ssn, :licensePlate, :licensePlateState, :passport, :tin, :searchIndicator, :gender, :occupation,:medProfLicense, :driversLicenseNumber, :driversLicenseState , :subjectOfInvestigation, :notes, :source, :vin)";

  private static final String SQL_INSERT_REFERRAL_ENTITY_SCIF =
      "INSERT INTO ISO21.V_REFER_ENTITY (I_ISO_REFR, I_CUST, C_ENTY_TYP, M_FST, M_LST, C_ROLE, M_CMT) VALUES (:referralId, :companyId, :type, :firstName, :lastName, :role, :notes ) ";

  private static final String SQL_REFERRAL_REASON =
      "INSERT INTO ISO21.V_REFER_REASON (I_ISO_REFR, N_ISO_REFR_RSN_SEQ, T_REFR_RSN) VALUES (:referralId, :referralSequence, :reasonText)";

  private static final String SQL_REFERRAL_LOG =
      "INSERT INTO ISO21.V_REFER_CHNG_LOG (I_ISO_REFR, I_CUST, I_CHNG_USER, I_USR_CREAT, H_AUD_CREAT_DT) VALUES (:referralId, :companyId, :changedBy, :createdBy, :auditDate)";

  private static final String SQL_INSERT_REFERRAL_PHONE_DETAILS =
      "INSERT INTO ISO21.V_REFER_ENTITY_TEL (I_ISO_REFR,I_REFR_ENTY,C_TEL_TYP,I_CUST,N_AREA,N_TEL,N_PIN,C_ROLE,F_SUBJ_INVST) VALUES (:referralId, :referralEntityId, :type, :companyId, :areaCode, :number, :pin, :role, :subjectOfInvestigation)";

  private static final String SQL_SELECT_REFERRAL_ID =
      "SELECT I_ISO_REFR FROM ISO21.V_REFER where I_CUST=:companyId and N_REFR=:referralNumber and D_RCV =:dateReceived";

  private static final String SQL_SELECT_REFERRAL_ENTITY_ID =
      "SELECT I_REFR_ENTY FROM ISO21.V_REFER_ENTITY WHERE I_ISO_REFR = :referralId and I_CUST=:companyId and D_ORIG = :dateReceived";

  private static final String SQL_SELECT_REFERRAL_ENTITY_ID_SCIF =
      "SELECT I_REFR_ENTY FROM ISO21.V_REFER_ENTITY WHERE I_ISO_REFR = :referralId and I_CUST=:companyId";

  private static final String SQL_SELECT_CLAIM =
      "SELECT T1.N_CLM, T1.N_POL, T1.I_ALLCLM, T1.D_OCUR, T1.T_LOL_STR1, T1.M_LOL_CITY, T1.C_LOL_ST_ALPH, "
          + "T1.I_CUST FROM ISO21.V_CLAIM T1 WHERE T1.I_ALLCLM = :allClaimId AND T1.F_VOID = ' ' AND "
          + "T1.I_CUST IN (SELECT AFFILIATE_CODE FROM NATB.INSATAB WHERE MEMBER_CODE IN (SELECT MAIN_AFFILIATE FROM NATB.INSMTAB WHERE MEMCOMP = :companyCode) OR AFFILIATE_CODE = :companyCode)";

  private static final String SQL_SELECT_CLM_ENTITY =
      "SELECT T1.I_ALLCLM, T1.I_NM_ADR, T1.M_FUL_NM, T1.C_NM_TYP, T2.I_NM_ADR AS AKA_PARENT, T3.I_NM_ADR as SP_PARENT, "
          + "T1.T_ADR_LN1, T1.T_ADR_LN2, T1.M_CITY, T1.C_ST_ALPH, T1.C_ZIP, T1.C_CNTRY, T1.C_GEND, T1.D_BRTH, T1.N_PSPRT, T4.N_SSN, T5.N_TIN, T6.T_OCCP, T7.N_PROF_MED_LIC, "
          + "T8.N_DRV_LIC, T8.C_ST_ALPH AS DRV_LIC_ST, T9.N_AREA AS HOME_AREA, T9.N_TEL AS HOME_TEL, T10.N_AREA AS BUS_AREA, T10.N_TEL AS BUS_TEL, T11.N_AREA AS CELL_AREA, T11.N_TEL AS CELL_TEL, "
          + "T12.N_AREA AS PAGE_AREA, T12.N_TEL AS PAGE_TEL, T12.N_PIN, T13.N_VIN, T13.N_LIC_PLT, T13.C_ST_ALPH AS LIC_PLT_ST, T14.T_CD_DSC AS IP_ROLE, T15.T_CD_DSC AS SP_ROLE "
          + "FROM ISO21.V_CLM_NM_ADR T1 "
          + "left outer join ISO21.V_CLM_NM_AKA T2 on T1.I_ALLCLM = T2.I_ALLCLM AND T1.I_NM_ADR = T2.I_NM_ADR_AKA "
          + "left outer join ISO21.V_SVC_PRVD T3 on T1.I_ALLCLM = T3.I_ALLCLM AND T1.I_NM_ADR = T3.I_NM_ADR_SVC_PRVD "
          + "left outer join ISO21.V_SSN T4 on T1.I_ALLCLM = T4.I_ALLCLM AND T1.I_NM_ADR = T4.I_NM_ADR "
          + "left outer join ISO21.V_TIN T5 on T1.I_ALLCLM = T5.I_ALLCLM AND T1.I_NM_ADR = T5.I_NM_ADR "
          + "left outer join ISO21.V_CLM_NM_INJ_OCCP T6 on T1.I_ALLCLM = T6.I_ALLCLM AND T1.I_NM_ADR = T6.I_NM_ADR "
          + "left outer join ISO21.V_PROF_MED_LIC T7 on T1.I_ALLCLM = T7.I_ALLCLM AND T1.I_NM_ADR = T7.I_NM_ADR "
          + "left outer join ISO21.V_DRV_LIC T8 on T1.I_ALLCLM = T8.I_ALLCLM AND T1.I_NM_ADR = T8.I_NM_ADR "
          + "left outer join ISO21.V_TEL T9 on T1.I_ALLCLM = T9.I_ALLCLM AND T1.I_NM_ADR = T9.I_NM_ADR AND T9.C_TEL_TYP = 'H' "
          + "left outer join ISO21.V_TEL T10 on T1.I_ALLCLM = T10.I_ALLCLM AND T1.I_NM_ADR = T10.I_NM_ADR AND T10.C_TEL_TYP = 'B' "
          + "left outer join ISO21.V_TEL T11 on T1.I_ALLCLM = T11.I_ALLCLM AND T1.I_NM_ADR = T11.I_NM_ADR AND T11.C_TEL_TYP = 'C' "
          + "left outer join ISO21.V_TEL T12 on T1.I_ALLCLM = T12.I_ALLCLM AND T1.I_NM_ADR = T12.I_NM_ADR AND T12.C_TEL_TYP = 'P' "
          + "left outer join ISO21.V_NM_VEH_LIC T13 on  T1.I_ALLCLM = T13.I_ALLCLM AND T1.I_NM_ADR = T13.I_NM_ADR "
          + "left outer join ISO21.V_UNIV_SIUCM T14 on T14.M_CD_COLM = 'UF_IP_ROLE' AND T14.I_CUST = 'iso' AND T1.C_ROLE = T14.C_CD_VAL "
          + "left outer join ISO21.V_UNIV_SIUCM T15 on T15.M_CD_COLM = 'UF_SP_ROLE' AND T15.I_CUST = 'iso' AND T1.C_ROLE = T15.C_CD_VAL "
          + "WHERE T1.I_ALLCLM = :allClaimId ";

  private static final String SQL_UNIV_TYPE =
      "SELECT C_UNIV	FROM ISO21.V_MBR_ELIG WHERE I_INSCOMP = :companyId AND C_UNIV_TYP = :univType";
  private static final String SQL_CLAIM_NUMBER_COUNT =
      "SELECT COUNT(I_ISO_REFR) CNT FROM ISO21.V_REFER WHERE N_CLM = :searchNum AND I_CUST = :companyId";

  private static final String SQL_SELECT_CLAIM_COMPANY =
      "SELECT I_CUST FROM ISO21.V_CLAIM WHERE I_ALLCLM = :allClaimId";

  private static final String SQL_SELECT_CUSTOMER_SEQUENCE =
      "SELECT * FROM iso21.V_CUST_REFR_SEQ where I_CUST = :companyID";

  private static final String SQL_UPDATE_CUSTOMER_SEQUENCE =
      "UPDATE ISO21.V_CUST_REFR_SEQ SET T_REFR_GRP=:groupNumber ,N_SEQ_TRK=:sequenceNumber WHERE I_CUST = :companyID";

  private static final String SQL_UPDATE_GENERATE_CUSTOMER_SEQUENCE =
      "UPDATE ISO21.V_CUST_REFR_SEQ SET N_SEQ_TRK = N_SEQ_TRK + 1 WHERE I_CUST =:companyID";

  public ClaimReferral getClaimReferral(String allClaimId, String companyCode) {

    Map<String, String> queryParameters = buildClaimQueryParameters(allClaimId, companyCode);

    List claimReferralObjectList =
        db2NamedParameterJdbcTemplate.query(SQL_SELECT_CLAIM, queryParameters, claimRowMapper);
    ClaimReferral claimReferral = null;
    if (null != claimReferralObjectList && claimReferralObjectList.size() > 0) {
      claimReferral = (ClaimReferral) claimReferralObjectList.get(0);
    }

    return claimReferral;
  }

  public List<ReferralEntity> getReferralEntities(String allClaimId) {

    Map<String, String> queryParameters = buildClaimQueryParameters(allClaimId);

    List<ReferralEntity> referralEntities =
        (List<ReferralEntity>) db2NamedParameterJdbcTemplate.query(SQL_SELECT_CLM_ENTITY,
            queryParameters, referralEntitiesRowMapper);

    return referralEntities;
  }

  public String getUnivValueByCompIdAndType(String compId, String univType) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("companyId", compId);
    queryParameters.put("univType", univType);

    String univTyp =
        db2NamedParameterJdbcTemplate.queryForObject(SQL_UNIV_TYPE, queryParameters, String.class);
    if (null != univTyp) {
      return univTyp;
    }
    return "";
  }

  public int getCountByClaimNumber(String compId, String claimNumber) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("companyId", compId);
    queryParameters.put("searchNum", claimNumber);

    int count =
        db2NamedParameterJdbcTemplate.queryForObject(SQL_CLAIM_NUMBER_COUNT, queryParameters,
            Integer.class);

    return count;
  }

  @Transactional
  public String addReferral(Referral referral) {

    MapSqlParameterSource parameters = buildReferralQueryParameters(referral);
    System.out.println("Parameters are " + parameters.toString());
    int rows = db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL, parameters);
    int reasonCount = 0;
    int entityCount = 0;

    if (rows > 0) {

      Map<String, String> queryParameters = new HashMap<String, String>();
      queryParameters.put("companyId", referral.getCompanyId());
      queryParameters.put("referralNumber", referral.getReferralNumber());
      queryParameters.put("dateReceived", referral.getDateReceived().toString());
      Long referralId =
          db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_REFERRAL_ID, queryParameters,
              Long.class);
      referral.setReferralId(String.valueOf(referralId));

      Map<String, String> reasonParameters = buildReferralReasonQueryParameters(referral);

      reasonCount = db2NamedParameterJdbcTemplate.update(SQL_REFERRAL_REASON, reasonParameters);
      referralLog(referral);
    }
    if (reasonCount > 0) {

      List referralEntities = referral.getReferralEntities();

      if (null != referralEntities && referralEntities.size() > 0) {

        entityCount = addReferralEntities(referral, referralEntities);
      }

    }

    return (entityCount > 0 ? Constants.SUCCESS : Constants.FAILED);
  }

  @Transactional
  public String addReferralSCIF(Referral referral) {

    MapSqlParameterSource parameters = buildReferralQueryParametersSCIF(referral);
    System.out.println("Parameters are " + parameters.toString());
    int rows = db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL_SCIF, parameters);
    int reasonCount = 0;
    int entityCount = 0;

    if (rows > 0) {

      Map<String, String> queryParameters = new HashMap<String, String>();
      queryParameters.put("companyId", referral.getCompanyId());
      queryParameters.put("referralNumber", referral.getReferralNumber());
      queryParameters.put("dateReceived", referral.getDateReceived().toString());
      Long referralId =
          db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_REFERRAL_ID, queryParameters,
              Long.class);
      referral.setReferralId(String.valueOf(referralId));

      Map<String, String> reasonParameters = buildReferralReasonQueryParameters(referral);

      reasonCount = db2NamedParameterJdbcTemplate.update(SQL_REFERRAL_REASON, reasonParameters);
      // referralLog(referral);
    }
    if (reasonCount > 0) {

      List referralEntities = referral.getReferralEntities();

      if (null != referralEntities && referralEntities.size() > 0) {

        entityCount = addReferralEntitiesScif(referral, referralEntities);
      }

    }

    return (rows > 0 ? Constants.SUCCESS : Constants.FAILED);
  }

  @Transactional
  public int addReferralEntities(Referral referral, List referralEntities) {

    int entityCount = 0;
    for (int i = 0; i < referralEntities.size(); i++) {

      Timestamp rightNow = new Timestamp(new Date().getTime());
      ReferralEntity referralEntity = (ReferralEntity) referralEntities.get(i);
      MapSqlParameterSource parameters = buildReferralEntityQueryParameters(referralEntity);
      parameters.addValue("referralId", referral.getReferralId());
      parameters.addValue("dateReceived", rightNow);

      entityCount = db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL_ENTITY, parameters);

      Map queryParameters = new HashMap();
      queryParameters.put("referralId", referral.getReferralId());
      queryParameters.put("companyId", referral.getCompanyId());
      queryParameters.put("dateReceived", rightNow);

      Integer referralEntityId =
          db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_REFERRAL_ENTITY_ID,
              queryParameters, Integer.class);
      referralEntity.setId(referralEntityId);

      if (referralEntity.getPhone() != null && referralEntity.getPhone().size() > 0)
        addReferralPhones(referral, referralEntity);

      addChildReferralEntities(referral, referralEntity);
    }
    return entityCount;
  }

  @Transactional
  public int addReferralEntitiesScif(Referral referral, List referralEntities) {

    int entityCount = 0;
    for (int i = 0; i < referralEntities.size(); i++) {

      Timestamp rightNow = new Timestamp(new Date().getTime());
      ReferralEntity referralEntity = (ReferralEntity) referralEntities.get(i);
      MapSqlParameterSource parameters =
          buildreferralEntityparametersforScif(referralEntity, referral);
      parameters.addValue("referralId", referral.getReferralId());
      parameters.addValue("dateReceived", rightNow);

      entityCount =
          db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL_ENTITY_SCIF, parameters);

      Map queryParameters = new HashMap();
      queryParameters.put("referralId", referral.getReferralId());
      queryParameters.put("companyId", referral.getCompanyId());

      Integer referralEntityId =
          db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_REFERRAL_ENTITY_ID_SCIF,
              queryParameters, Integer.class);
      referralEntity.setId(referralEntityId);

      if (referralEntity.getPhone() != null && referralEntity.getPhone().size() > 0)
        addReferralPhones(referral, referralEntity);

      addChildReferralEntities(referral, referralEntity);
    }
    return entityCount;
  }

  @Transactional
  public void addChildReferralEntities(Referral referral, ReferralEntity referralEntity) {

    List akas = referralEntity.getAkas();
    List sps = referralEntity.getServiceProviders();

    List allChildEntities = new ArrayList<>();
    allChildEntities.addAll(akas);
    allChildEntities.addAll(sps);

    if (allChildEntities.size() > 0) {
      for (int i = 0; i < allChildEntities.size(); i++) {

        Timestamp rightNow = new Timestamp(new Date().getTime());
        ReferralEntity referralChildEntity = (ReferralEntity) allChildEntities.get(i);
        referralChildEntity.setParentEntityId(referralEntity.getId());

        MapSqlParameterSource parameters = buildReferralEntityQueryParameters(referralChildEntity);
        parameters.addValue("referralId", referral.getReferralId());
        parameters.addValue("dateReceived", rightNow);

        db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL_ENTITY, parameters);

        Map queryParameters = new HashMap();
        queryParameters.put("referralId", referral.getReferralId());
        queryParameters.put("companyId", referral.getCompanyId());
        queryParameters.put("dateReceived", rightNow);

        Integer childreferralEntityId =
            db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_REFERRAL_ENTITY_ID,
                queryParameters, Integer.class);
        referralChildEntity.setId(childreferralEntityId);

        if (referralChildEntity.getPhone() != null && referralChildEntity.getPhone().size() > 0)
          addReferralPhones(referral, referralChildEntity);
      }
    }
  }

  @Transactional
  public void referralLog(Referral referral) {

    MapSqlParameterSource parameters = buildReferralLogQueryParameters(referral);
    int rows = db2NamedParameterJdbcTemplate.update(SQL_REFERRAL_LOG, parameters);

  }

  @Transactional
  protected void addReferralPhones(Referral referral, ReferralEntity referralEntity) {

    for (int i = 0; i < referralEntity.getPhone().size(); i++) {

      Phone phone = referralEntity.getPhone().get(i);
      MapSqlParameterSource parameters =
          buildReferralEntityPhoneParameters(referral, referralEntity, phone);
      int phones =
          db2NamedParameterJdbcTemplate.update(SQL_INSERT_REFERRAL_PHONE_DETAILS, parameters);
    }
  }

  protected Map<String, String> buildReferralReasonQueryParameters(Referral referral) {

    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("referralId", referral.getReferralId());
    queryParameters.put("referralSequence", "0");
    queryParameters.put("reasonText", referral.getReasonText());
    return queryParameters;
  }

  protected Map<String, String> buildClaimQueryParameters(String allClaimId, String companyCode) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("allClaimId", allClaimId);
    queryParameters.put("companyCode", companyCode);
    return queryParameters;
  }

  protected Map<String, String> buildClaimQueryParameters(String allClaimId) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    queryParameters.put("allClaimId", allClaimId);
    return queryParameters;
  }

  protected MapSqlParameterSource buildReferralQueryParameters(Referral referral) {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("referralNumber", referral.getReferralNumber());
    parameters.addValue("claimNumber", referral.getClaimNumber());
    parameters.addValue("dateOfLoss", referral.getDateOfLoss());
    parameters.addValue("policyNumber", referral.getPolicyNumber());
    parameters.addValue("lolAddress", referral.getLolAddress() != null ? referral.getLolAddress()
        : "");
    parameters.addValue("lolCity", referral.getLolCity() != null ? referral.getLolCity() : "");
    parameters.addValue("lolState", referral.getLolState() != null ? referral.getLolState() : "");
    parameters.addValue("state", referral.getState() != null ? referral.getState() : "");
    parameters.addValue("allclmId", referral.getAllClaimId() != null ? referral.getAllClaimId()
        : "");
    parameters.addValue("companyId", referral.getCompanyId());
    parameters.addValue("dateAssigned", referral.getDateAssigned());
    parameters.addValue("dateReceived", referral.getDateReceived());
    parameters.addValue("lossType", referral.getLossType());
    parameters.addValue("lossSubType", referral.getLossSubType());
    // parameters.addValue("reasonText", referral.getReasonText());
    parameters.addValue("businessUnit",
        referral.getBusinessUnit() != null ? referral.getBusinessUnit() : "");
    parameters.addValue("city", referral.getCity() != null ? referral.getCity() : "");
    parameters.addValue("managerQueue",
        referral.getManagerQueue() != null ? referral.getManagerQueue() : "");
    parameters.addValue("source", referral.getSource() != null ? referral.getSource() : "");
    parameters.addValue("referralType",
        referral.getReferralType() != null ? referral.getReferralType() : "");
    parameters
        .addValue("createdBy", referral.getCreatedBy() != null ? referral.getCreatedBy() : "");
    parameters.addValue("referralStatus",
        referral.getReferralStatus() != null ? referral.getReferralStatus() : "");
    parameters.addValue("suspicionReason",
        referral.getSuspicionReason() != null ? referral.getSuspicionReason() : "");

    return parameters;

  }

  protected MapSqlParameterSource buildReferralQueryParametersSCIF(Referral referral) {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("referralNumber", referral.getReferralNumber());
    parameters.addValue("claimNumber", referral.getClaimNumber());
    parameters.addValue("dateOfLoss", referral.getDateOfLoss());
    parameters.addValue("policyNumber", referral.getPolicyNumber());
    parameters.addValue("lolAddress", referral.getLolAddress() != null ? referral.getLolAddress()
        : "");
    parameters.addValue("lolCity", referral.getLolCity() != null ? referral.getLolCity() : "");
    parameters.addValue("lolState", referral.getLolState() != null ? referral.getLolState() : "");
    parameters.addValue("state", referral.getState() != null ? referral.getState() : "");
    parameters.addValue("allclmId", referral.getAllClaimId() != null ? referral.getAllClaimId()
        : "");
    parameters.addValue("companyId", referral.getCompanyId());
    parameters.addValue("dateAssigned", referral.getDateAssigned());
    parameters.addValue("dateReceived", referral.getDateReceived());
    parameters.addValue("lossType", referral.getLossType());
    parameters.addValue("lossSubType", referral.getLossSubType());
    // parameters.addValue("reasonText", referral.getReasonText());
    parameters.addValue("businessUnit",
        referral.getBusinessUnit() != null ? referral.getBusinessUnit() : "");
    parameters.addValue("city", referral.getCity() != null ? referral.getCity() : "");
    parameters.addValue("managerQueue",
        referral.getManagerQueue() != null ? referral.getManagerQueue() : "");
    parameters.addValue("source", referral.getSource() != null ? referral.getSource() : "");
    parameters.addValue("referralType",
        referral.getReferralType() != null ? referral.getReferralType() : "");
    parameters
        .addValue("createdBy", referral.getCreatedBy() != null ? referral.getCreatedBy() : "");
    parameters.addValue("referralStatus",
        referral.getReferralStatus() != null ? referral.getReferralStatus() : "");
    parameters.addValue("suspicionReason",
        referral.getSuspicionReason() != null ? referral.getSuspicionReason() : "");
    parameters
        .addValue("agentName", referral.getAgentName() != null ? referral.getAgentName() : "");
    parameters.addValue("agentEmail", referral.getAgentEmail() != null ? referral.getAgentEmail()
        : "");

    return parameters;

  }

  protected MapSqlParameterSource buildReferralEntityQueryParameters(ReferralEntity referralEntity) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("companyId", referralEntity.getCompanyId());
    parameters.addValue("type", referralEntity.getType());
    parameters.addValue("parentEntity",
        referralEntity.getParentEntityId() != null ? referralEntity.getParentEntityId() : null);
    parameters.addValue("businessName",
        referralEntity.getBusinessName() != null ? referralEntity.getBusinessName() : "");
    parameters.addValue("role", referralEntity.getRole() != null ? referralEntity.getRole() : "");
    parameters.addValue("firstName",
        referralEntity.getFirstName() != null ? referralEntity.getFirstName() : "");
    parameters.addValue("middleName",
        referralEntity.getMiddleName() != null ? referralEntity.getMiddleName() : "");
    parameters.addValue("lastName",
        referralEntity.getLastName() != null ? referralEntity.getLastName() : "");
    parameters.addValue("address1",
        referralEntity.getAddress1() != null ? referralEntity.getAddress1() : "");
    parameters.addValue("address2",
        referralEntity.getAddress2() != null ? referralEntity.getAddress2() : "");
    parameters.addValue("city", referralEntity.getCity() != null ? referralEntity.getCity() : "");
    parameters
        .addValue("state", referralEntity.getState() != null ? referralEntity.getState() : "");
    parameters.addValue("zipCode",
        referralEntity.getZipCode() != null ? referralEntity.getZipCode() : "");
    parameters.addValue("country",
        referralEntity.getCountry() != null ? referralEntity.getCountry() : "");
    parameters.addValue("dateOfBirth", referralEntity.getDateOfBirth());
    parameters.addValue("ssn", referralEntity.getSsn() != null ? referralEntity.getSsn() : "");
    parameters.addValue("licensePlate",
        referralEntity.getLicensePlate() != null ? referralEntity.getLicensePlate() : "");
    parameters.addValue("licensePlateState",
        referralEntity.getLicensePlateState() != null ? referralEntity.getLicensePlateState() : "");
    parameters.addValue("passport",
        referralEntity.getPassport() != null ? referralEntity.getPassport() : "");
    parameters.addValue("tin", referralEntity.getTin() != null ? referralEntity.getTin() : "");
    parameters.addValue("searchIndicator", referralEntity.isSearchIndicator() ? "Y" : "N");
    parameters.addValue("gender", referralEntity.getGender() != null ? referralEntity.getGender()
        : "");
    parameters.addValue("occupation",
        referralEntity.getOccupation() != null ? referralEntity.getOccupation() : "");
    parameters.addValue("medProfLicense",
        referralEntity.getMedProfLicense() != null ? referralEntity.getMedProfLicense() : "");
    parameters.addValue("driversLicenseNumber",
        referralEntity.getDriversLicenseNumber() != null ? referralEntity.getDriversLicenseNumber()
            : "");
    parameters.addValue("subjectOfInvestigation", referralEntity.isSubjectOfInvestigation() ? "Y"
        : "N");
    parameters.addValue("driversLicenseState",
        referralEntity.getDriversLicenseState() != null ? referralEntity.getDriversLicenseState()
            : "");
    parameters
        .addValue("notes", referralEntity.getNotes() != null ? referralEntity.getNotes() : "");
    parameters.addValue("source", referralEntity.getSource() != null ? referralEntity.getSource()
        : "");
    parameters.addValue("vin", referralEntity.getVin() != null ? referralEntity.getVin() : "");

    return parameters;
  }

  protected MapSqlParameterSource buildreferralEntityparametersforScif(
      ReferralEntity referralEntity, Referral referral) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("role", referralEntity.getRole());
    parameters.addValue("firstName", referralEntity.getFirstName());
    parameters.addValue("lastName", referralEntity.getLastName());
    parameters.addValue("companyId", referral.getCompanyId());
    StringBuffer notesStrBuf = new StringBuffer();
    if (!referral.getGroup().equals("")) {
      notesStrBuf.append("Group : ").append(referral.getGroup());
    }
    if (!referral.getPolicyYear().equals("")) {
      notesStrBuf.append("  Policy Year : ").append(referral.getPolicyYear());
    }
    if (!referral.getAgentEmail().equals("")) {
      notesStrBuf.append("  Email : ").append(referral.getAgentEmail());
    }
    parameters.addValue("notes", notesStrBuf.toString());
    // "Group : " + referral.getGroup() + ",  Policy Year : "+ referral.getPolicyYear());
    parameters.addValue("type", "0001");

    return parameters;
  }

  protected MapSqlParameterSource buildReferralLogQueryParameters(Referral referral) {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("referralId", referral.getReferralId());
    parameters.addValue("companyId", referral.getCompanyId());
    parameters.addValue("changedBy", referral.getManagerQueue());
    parameters.addValue("createdBy", referral.getCreatedBy());
    parameters.addValue("auditDate", referral.getDateReceived());

    return parameters;

  }

  protected MapSqlParameterSource buildReferralEntityPhoneParameters(Referral referral,
      ReferralEntity referralEntity, Phone phone) {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    parameters.addValue("referralId", referral.getReferralId());
    parameters.addValue("referralEntityId", referralEntity.getId());
    parameters.addValue("type", phone.getType());
    parameters.addValue("companyId", referral.getCompanyId());
    parameters.addValue("areaCode", phone.getAreaCode());
    parameters.addValue("number", phone.getNumber());
    parameters.addValue("pin", phone.getPin());
    parameters.addValue("role", phone.getRole() != null ? phone.getRole() : "");
    parameters.addValue("subjectOfInvestigation", referralEntity.isSubjectOfInvestigation());

    return parameters;

  }

  public String getCompanyByClaimId(String allClaimId) {

    Map<String, String> queryParameters = buildClaimQueryParameters(allClaimId);

    String customerId =
        (String) db2NamedParameterJdbcTemplate.queryForObject(SQL_SELECT_CLAIM_COMPANY,
            queryParameters, String.class);

    return customerId;
  }

  public String generateReferralNumberFromCustomerSequence(String companyCode) {

    Calendar currentDate = Calendar.getInstance();
    String currentYear = "" + currentDate.get(Calendar.YEAR);
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("companyID", companyCode);

    db2NamedParameterJdbcTemplate.update(SQL_UPDATE_GENERATE_CUSTOMER_SEQUENCE, parameters);

    CustomerSequence custSequence =
        (CustomerSequence) db2NamedParameterJdbcTemplate.queryForObject(
            SQL_SELECT_CUSTOMER_SEQUENCE, parameters, customerSequenceRowMapper);
    int currentSequenceNumber = custSequence.getSequenceNumber();

    if (!currentYear.equals(custSequence.getSequenceGroup())) {
      currentSequenceNumber = 1;
      custSequence.setSequenceNumber(currentSequenceNumber);
      custSequence.setSequenceGroup(currentYear);

      parameters.addValue("groupNumber", currentYear);
      parameters.addValue("sequenceNumber", currentSequenceNumber);
      parameters.addValue("companyID", companyCode);
      db2NamedParameterJdbcTemplate.update(SQL_UPDATE_CUSTOMER_SEQUENCE, parameters);
    }

    return currentYear + StringUtils.leftPad("" + (currentSequenceNumber), 6, "0");
  }

  public void setDb2NamedParameterJdbcTemplate(
      NamedParameterJdbcTemplate db2NamedParameterJdbcTemplate) {
    this.db2NamedParameterJdbcTemplate = db2NamedParameterJdbcTemplate;
  }
}
