package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.MyCase;

/**
 * This class is responsible for mapping Case information in the ClaimSearch database to MyCase
 * object based on a given Case details.
 * 
 * @author i90845
 */
public class MyCasesRowMapper implements RowMapper<MyCase> {

  @Autowired
  public MyCase mapRow(ResultSet rs, int RowNum) throws SQLException {

    MyCase myCase = new MyCase();

    myCase.setCaseId(rs.getInt("I_ISO_CASE"));
    myCase.setCaseNumber(cleanStringColumn(rs, "N_CASE"));
    myCase.setCity(cleanStringColumn(rs, "M_CITY"));
    myCase.setState(cleanStringColumn(rs, "C_ST_ALPH"));
    myCase.setDescription(cleanStringColumn(rs, "T_CASE_DSC"));
    myCase.setLastUpdated(cleanStringColumn(rs, "H_REC_MODF"));

    return myCase;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
