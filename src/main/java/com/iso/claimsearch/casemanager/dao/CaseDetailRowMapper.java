package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.CaseDetails;


/**
 * This class is responsible for mapping Case information in the ClaimSearch database to MyCase
 * object based on a given Case details.
 * 
 * @author i90845
 */
public class CaseDetailRowMapper implements RowMapper<CaseDetails> {

  @Autowired
  public CaseDetails mapRow(ResultSet rs, int RowNum) throws SQLException {

    CaseDetails caseDetails = new CaseDetails();

    caseDetails.setCaseId(rs.getInt("I_ISO_CASE"));
    caseDetails.setCaseNumber(cleanStringColumn(rs, "N_CASE"));
    caseDetails.setLastUpdated(cleanStringColumn(rs, "H_REC_MODF"));
    caseDetails.setCity(cleanStringColumn(rs, "M_CITY"));
    caseDetails.setState(cleanStringColumn(rs, "C_ST_ALPH"));

    return caseDetails;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
