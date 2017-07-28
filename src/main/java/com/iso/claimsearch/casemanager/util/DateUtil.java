package com.iso.claimsearch.casemanager.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtil {

  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private DateUtil() {}


  public static Date getFormattedDate(Date date) {
    String formattedDate = dateFormat.format(date);
    try {
      return dateFormat.parse(formattedDate);
    } catch (ParseException e) {
      return date;
    }
  }


}
