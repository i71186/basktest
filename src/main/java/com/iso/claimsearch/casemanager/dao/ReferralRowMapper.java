package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.Referral;

/**
 * This class is responsible for mapping Referral information in the ClaimSearch database to
 * Referral object based on a given Claim ID.
 * 
 * @author
 */

public class ReferralRowMapper implements RowMapper<Referral> {

  @Autowired
  public Referral mapRow(ResultSet rs, int RowNum) throws SQLException {

    Referral referral = new Referral();
    referral.setCompanyId(cleanStringColumn(rs, "I_CUST"));
    referral.setReferralNumber(cleanStringColumn(rs, "N_REFR"));
    referral.setSource(cleanStringColumn(rs, "I_USER"));
    referral.setManagerQueue(cleanStringColumn(rs, "I_USER_MGR"));
    referral.setDateReceived(rs.getDate("D_RCV"));
    referral.setCity(cleanStringColumn(rs, "M_CITY"));
    referral.setState(cleanStringColumn(rs, "C_ST_ALPH"));
    referral.setCounty(cleanStringColumn(rs, "C_COUNTY_SIUC"));
    referral.setDepartment(cleanStringColumn(rs, "C_CO_DPT"));
    referral.setBusinessUnit(cleanStringColumn(rs, "C_CO_BUS_UNIT"));
    referral.setOffice(cleanStringColumn(rs, "C_CO_OFC"));
    referral.setPolicyNumber(cleanStringColumn(rs, "N_POL"));
    referral.setAgentName(cleanStringColumn(rs, "M_AGT"));
    referral.setAgentAgency(cleanStringColumn(rs, "M_AGCY"));
    referral.setAgentPhone(cleanStringColumn(rs, "N_AGT_TEL"));
    referral.setAgentEmail(cleanStringColumn(rs, "I_EMAIL"));
    referral.setSuspicionReason(cleanStringColumn(rs, "C_RSN_SUSP"));
    referral.setReferralStatus(cleanStringColumn(rs, "C_REF_STUS"));
    referral.setCreatedBy(cleanStringColumn(rs, "I_USR_CREAT"));
    referral.setClaimNumber(cleanStringColumn(rs, "N_CLM"));
    referral.setDateOfLoss(rs.getDate("D_OCUR"));
    referral.setLossType(cleanStringColumn(rs, "C_LOSS_TYP"));
    referral.setLossSubType(cleanStringColumn(rs, "C_LOSS_SUB_TYP"));
    referral.setLolAddress(cleanStringColumn(rs, "M_LOL_ADR_LN1"));
    referral.setLolCity(cleanStringColumn(rs, "M_LOL_CITY"));
    referral.setLolState(cleanStringColumn(rs, "C_LOL_ST_ALPH"));
    referral.setReferralType(cleanStringColumn(rs, "I_REFR_TYP"));
    referral.setReferralId(cleanStringColumn(rs, "I_ISO_REFR"));
    return referral;

  }

  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }
}
