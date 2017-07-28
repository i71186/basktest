package com.iso.claimsearch.casemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.iso.claimsearch.casemanager.model.Phone;
import com.iso.claimsearch.casemanager.model.ReferralEntity;

/**
 * This class is responsible for mapping Referral Entity information in the ClaimSearch database to
 * Referral Entity object based on a given Claim ID.
 * 
 * @author
 */

public class ReferralEntitiesRowMapper implements RowMapper<ReferralEntity> {

  @Autowired
  public ReferralEntity mapRow(ResultSet rs, int RowNum) throws SQLException {

    ReferralEntity referralEntity = new ReferralEntity();

    String name = cleanStringColumn(rs, "M_FUL_NM");
    String nameType = cleanStringColumn(rs, "C_NM_TYP");
    if ("I".equals(nameType)) {
      String[] names = null;
      names = name.split(",");
      if (names.length >= 2) {
        referralEntity.setLastName(names[0]);
        referralEntity.setFirstName(names[1]);
      }
      if (names.length == 3) {
        referralEntity.setMiddleName(names[2]);
      }
    } else {
      referralEntity.setBusinessName(name);
    }

    String relat = cleanStringColumn(rs, "I_NM_ADR");
    String akaAddr = cleanStringColumn(rs, "AKA_PARENT");
    String spAddr = cleanStringColumn(rs, "SP_PARENT");
    // AKA
    if (!akaAddr.equals("")) {
      relat += ", AKA - " + akaAddr;
      referralEntity.setRole(Constants.ENTITY_ROLE_AKA); // 0004
    }
    // Service Provider
    else if (!spAddr.equals("")) {
      relat += ", SP - " + spAddr;
      String sprole = cleanStringColumn(rs, "SP_ROLE");
      if (!sprole.equals(""))
        referralEntity.setRole(sprole);
    }
    // IP
    else {
      String iprole = cleanStringColumn(rs, "IP_ROLE");
      if (!iprole.equals(""))
        referralEntity.setRole(iprole);
    }

    referralEntity.setNotes(relat);

    String address1 = cleanStringColumn(rs, "T_ADR_LN1");
    if (!address1.equals(""))
      referralEntity.setAddress1(address1);

    String address2 = cleanStringColumn(rs, "T_ADR_LN2");
    if (!address2.equals(""))
      referralEntity.setAddress2(address2);

    String city = cleanStringColumn(rs, "M_CITY");
    if (!city.equals(""))
      referralEntity.setCity(city);

    String state = cleanStringColumn(rs, "C_ST_ALPH");
    if (!state.equals(""))
      referralEntity.setState(state);

    String zipCode = cleanStringColumn(rs, "C_ZIP");
    if (!zipCode.equals(""))
      referralEntity.setZipCode(zipCode);

    String country = cleanStringColumn(rs, "C_CNTRY");
    referralEntity.setCountry(country);

    String gender = cleanStringColumn(rs, "C_GEND");
    if ("M".equals(gender))
      referralEntity.setGender(Constants.ENTITY_GENDER_MALE);
    if ("F".equals(gender))
      referralEntity.setGender(Constants.ENTITY_GENDER_FEMALE);

    if (rs.getDate("D_BRTH") != null)
      referralEntity.setDateOfBirth(rs.getDate("D_BRTH"));

    String passport = cleanStringColumn(rs, "N_PSPRT");
    if (!passport.equals(""))
      referralEntity.setPassport(passport);

    String ssn = cleanStringColumn(rs, "N_SSN");
    if (!ssn.equals(""))
      referralEntity.setSsn(ssn);

    String tin = cleanStringColumn(rs, "N_TIN");
    if (!tin.equals(""))
      referralEntity.setTin(tin);

    String occupation = cleanStringColumn(rs, "T_OCCP");
    if (!occupation.equals(""))
      referralEntity.setOccupation(occupation);

    String medProfLicense = cleanStringColumn(rs, "N_PROF_MED_LIC");
    if (!medProfLicense.equals(""))
      referralEntity.setMedProfLicense(medProfLicense);

    String driversLicenseNumber = cleanStringColumn(rs, "N_DRV_LIC");
    if (!driversLicenseNumber.equals(""))
      referralEntity.setDriversLicenseNumber(driversLicenseNumber);

    String driversLicenseState = cleanStringColumn(rs, "DRV_LIC_ST");
    if (!driversLicenseState.equals(""))
      referralEntity.setDriversLicenseState(driversLicenseState);

    List phoneList = new ArrayList();

    Integer homeTelephone = rs.getInt("HOME_TEL");
    if (homeTelephone != 0) {
      Phone hphone = new Phone();
      hphone.setType(Constants.HOME_PHONE);
      Integer homeAreaCode = rs.getInt("HOME_AREA");
      if (homeAreaCode != null)
        hphone.setAreaCode(homeAreaCode);
      hphone.setNumber(homeTelephone);
      phoneList.add(hphone);
    }

    Integer businessTelephone = rs.getInt("BUS_TEL");
    if (businessTelephone != 0) {
      Phone bPhone = new Phone();
      bPhone.setType(Constants.BUSINESS_PHONE);
      Integer busAreaCode = rs.getInt("BUS_AREA");
      if (busAreaCode != null)
        bPhone.setAreaCode(busAreaCode);
      bPhone.setNumber(businessTelephone);
      phoneList.add(bPhone);
    }

    Integer cellTelephone = rs.getInt("CELL_TEL");
    if (businessTelephone != 0) {
      Phone cPhone = new Phone();
      cPhone.setType(Constants.CELL_PHONE);
      Integer cellAreaCode = rs.getInt("CELL_AREA");
      if (cellAreaCode != null)
        cPhone.setAreaCode(cellAreaCode);
      cPhone.setNumber(cellTelephone);
      phoneList.add(cPhone);
    }

    Integer pagerTelephone = rs.getInt("PAGE_TEL");
    if (pagerTelephone != 0) {
      Phone pPhone = new Phone();
      pPhone.setType(Constants.PAGER_PHONE);
      Integer pagerAreaCode = rs.getInt("PAGE_AREA");
      Integer pagerPin = rs.getInt("N_PIN");
      if (pagerAreaCode != null)
        pPhone.setAreaCode(pagerAreaCode);
      pPhone.setNumber(pagerTelephone);
      phoneList.add(pPhone);
    }

    referralEntity.setPhone(phoneList);

    String vin = cleanStringColumn(rs, "N_VIN");
    if (!vin.equals(""))
      referralEntity.setVin(vin);

    String licensePlate = cleanStringColumn(rs, "N_LIC_PLT");
    if (!licensePlate.equals(""))
      referralEntity.setLicensePlate(licensePlate);

    String licensePlateState = cleanStringColumn(rs, "LIC_PLT_ST");
    if (!licensePlateState.equals(""))
      referralEntity.setLicensePlateState(licensePlateState);

    return referralEntity;
  }


  private String cleanStringColumn(ResultSet rs, String columnName) throws SQLException {
    return StringUtils.trimToEmpty(rs.getString(columnName)).toUpperCase();
  }

}
