package com.iso.claimsearch.casemanager.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Model object for storing the address details
 * 
 * @author i94536
 */
public class Address implements Serializable {


  /**
	 * 
	 */
  private static final long serialVersionUID = 8761247302151144094L;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;

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

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
