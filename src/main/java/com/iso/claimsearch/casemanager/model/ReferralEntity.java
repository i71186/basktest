package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReferralEntity implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8542703027141574548L;

  private Integer referralId;
  private String companyId;
  private String type;
  private String role;
  private String businessName;
  private String firstName;
  private String lastName;
  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zipCode;
  private String country;
  private String passport;
  private boolean searchIndicator;
  private String gender;
  private boolean subjectOfInvestigation;
  private String notes;
  private String source;
  private Integer id;
  private String middleName;
  private String ssn;
  private String tin;
  private Date dateOfBirth;
  private String occupation;
  private String medProfLicense;
  private String driversLicenseNumber;
  private String driversLicenseState;
  private String licensePlate;
  private String licensePlateState;
  private String vin;
  private String employer;
  private Integer parentEntityId;
  private List akas = new LinkedList();
  private List serviceProviders = new LinkedList();
  private List<Phone> phone = new ArrayList<Phone>();

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public String getLicensePlateState() {
    return licensePlateState;
  }

  public void setLicensePlateState(String licensePlateState) {
    this.licensePlateState = licensePlateState;
  }

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public Integer getReferralId() {
    return referralId;
  }

  public void setReferralId(Integer referralId) {
    this.referralId = referralId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPassport() {
    return passport;
  }

  public void setPassport(String passport) {
    this.passport = passport;
  }

  public boolean isSearchIndicator() {
    return searchIndicator;
  }

  public void setSearchIndicator(boolean searchIndicator) {
    this.searchIndicator = searchIndicator;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public boolean isSubjectOfInvestigation() {
    return subjectOfInvestigation;
  }

  public void setSubjectOfInvestigation(boolean subjectOfInvestigation) {
    this.subjectOfInvestigation = subjectOfInvestigation;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getDriversLicenseState() {
    return driversLicenseState;
  }

  public void setDriversLicenseState(String driversLicenseState) {
    this.driversLicenseState = driversLicenseState;
  }

  public String getDriversLicenseNumber() {
    return driversLicenseNumber;
  }

  public void setDriversLicenseNumber(String driversLicenseNumber) {
    this.driversLicenseNumber = driversLicenseNumber;
  }

  public String getMedProfLicense() {
    return medProfLicense;
  }

  public void setMedProfLicense(String medProfLicense) {
    this.medProfLicense = medProfLicense;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getTin() {
    return tin;
  }

  public void setTin(String tin) {
    this.tin = tin;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public List<Phone> getPhone() {
    return phone;
  }

  public void setPhone(List<Phone> phone) {
    this.phone = phone;
  }

  public String getEmployer() {
    return employer;
  }

  public void setEmployer(String employer) {
    this.employer = employer;
  }

  public List getAkas() {
    return akas;
  }

  public void setAkas(List akas) {
    this.akas = akas;
  }

  public List getServiceProviders() {
    return serviceProviders;
  }

  public void setServiceProviders(List serviceProviders) {
    this.serviceProviders = serviceProviders;
  }

  public Integer getParentEntityId() {
    return parentEntityId;
  }

  public void setParentEntityId(Integer parentEntityId) {
    this.parentEntityId = parentEntityId;
  }
}
