package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.Address;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.Name;

/**
 * This class is responsible for mapping Case Entity information in the ClaimSearch database to
 * CaseEnitity object based on a given Case details.
 * 
 * @author i94536
 */

public class CaseEntitiesRowMapper implements RowMapper<CaseEntity> {

  @Autowired
  public CaseEntity mapRow(ResultSet rs, int RowNum) throws SQLException {

    CaseEntity caseEntity = new CaseEntity();

    Address address = CaseAddress(rs);
    Name name = CaseName(rs);
    caseEntity.setLastUpdated(cleanStringColumn(rs, "H_REC_MODF"));
    caseEntity.setCaseRole(cleanStringColumn(rs, "ROLE"));
    caseEntity.setName(name);
    caseEntity.setAddress(address);

    return caseEntity;
  }

  private Name CaseName(ResultSet rs) throws SQLException {

    Name name = new Name();

    name.setFirstName(cleanStringColumn(rs, "M_FST"));
    name.setMiddleName(cleanStringColumn(rs, "M_MID"));
    name.setLastName(cleanStringColumn(rs, "M_LST"));
    name.setBusinessName(cleanStringColumn(rs, "M_BUS_NM"));

    return name;
  }

  private Address CaseAddress(ResultSet rs) throws SQLException {

    Address address = new Address();
    String street = cleanStringColumn(rs, "T_ADR_LN1") + cleanStringColumn(rs, "T_ADR_LN2");
    address.setStreet(street);
    address.setCity(cleanStringColumn(rs, "M_CITY"));
    address.setState(cleanStringColumn(rs, "C_ST_ALPH"));
    address.setZip(cleanStringColumn(rs, "C_ZIP"));
    address.setCountry(cleanStringColumn(rs, "C_CNTRY"));

    return address;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
