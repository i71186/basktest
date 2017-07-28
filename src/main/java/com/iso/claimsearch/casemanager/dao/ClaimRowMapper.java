package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.ClaimReferral;

public class ClaimRowMapper implements RowMapper<ClaimReferral> {
  @Autowired
  public ClaimReferral mapRow(ResultSet rs, int RowNum) throws SQLException {

    ClaimReferral claimReferral = new ClaimReferral();

    if (rs != null) {

      claimReferral.setAllClaimId(cleanStringColumn(rs, "I_ALLCLM"));
      claimReferral.setClaimNumber(cleanStringColumn(rs, "N_CLM"));
      claimReferral.setCompanyId(cleanStringColumn(rs, "I_CUST"));
      claimReferral.setPolicyNumber(cleanStringColumn(rs, "N_POL"));
      claimReferral.setDateOfLoss(rs.getDate("D_OCUR"));
      claimReferral.setLolAddress(cleanStringColumn(rs, "T_LOL_STR1"));
      claimReferral.setLolCity(cleanStringColumn(rs, "M_LOL_CITY"));
      claimReferral.setLolState(cleanStringColumn(rs, "C_LOL_ST_ALPH"));
    }

    return claimReferral;
  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }



}
