package com.iso.claimsearch.casemanager.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class JsonDateDeserializer extends JsonDeserializer<Date> {

  private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

  @Override
  public Date deserialize(JsonParser parser, DeserializationContext ctxt) {
    String dateString = null;
    try {
      dateString = parser.getText();
      if (!dateString.equals("")) {
        Date date = dateFormat.parse(dateString);
        if (dateString.equals(dateFormat.format(date))) {
          return date;
        } else {
          throw new Exception();
        }
      }
      return null;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Date field: " + dateString, e);
    }
  }
}
