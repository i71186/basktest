package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.CustomerSequence;

public class CustomerSequenceRowMapper implements RowMapper<CustomerSequence> {

  @Autowired
  public CustomerSequence mapRow(ResultSet rs, int RowNum) throws SQLException {

    CustomerSequence custSequence = new CustomerSequence();

    custSequence.setCompanyId(cleanStringColumn(rs, "I_CUST"));
    custSequence.setSequenceGroup(cleanStringColumn(rs, "T_REFR_GRP"));
    custSequence.setSequenceNumber(rs.getInt("N_SEQ_TRK"));

    return custSequence;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
