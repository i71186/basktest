package com.iso.claimsearch.casemanager.service;

import java.util.List;

import com.iso.claimsearch.casemanager.model.CaseDetails;
import com.iso.claimsearch.casemanager.model.CaseEntity;

/**
 * Implementing classes are responsible for invoking appropriate method calls through DAO layer.
 * 
 * @author i94536
 */

public interface CaseEntityService {

  public List<CaseEntity> getCaseEntities(String caseId, String companyId);

  public CaseDetails getCaseDetails(String caseId);

}
