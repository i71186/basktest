package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.Name;

/**
 * This class is responsible for mapping Insured information in the ClaimSearch database to Name
 * object based on a given Case id.
 * 
 * @author i90845
 */
public class CaseInsuredRowMapper implements RowMapper<Name> {

  @Autowired
  public Name mapRow(ResultSet rs, int RowNum) throws SQLException {

    Name name = new Name();

    name.setFirstName(cleanStringColumn(rs, "M_FST"));
    name.setMiddleName(cleanStringColumn(rs, "M_MID"));
    name.setLastName(cleanStringColumn(rs, "M_LST"));
    name.setBusinessName(cleanStringColumn(rs, "M_BUS_NM"));

    return name;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
