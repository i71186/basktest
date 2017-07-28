package com.iso.claimsearch.casemanager.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CaseManagerWelcome {

  @RequestMapping("/casemanagerwelcome")
  public String caseManagerWelcome() {
    return "Welcome to SIU Case Manager!";
  }
}
