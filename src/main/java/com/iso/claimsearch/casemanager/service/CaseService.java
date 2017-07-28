package com.iso.claimsearch.casemanager.service;

import java.util.List;

import com.iso.claimsearch.casemanager.model.MyCase;


/**
 * Implementing classes are responsible for invoking appropriate method calls through DAO layer.
 * 
 * @author i90845
 */

public interface CaseService {

  public List<MyCase> getMyOpenCases(String userId);

}
