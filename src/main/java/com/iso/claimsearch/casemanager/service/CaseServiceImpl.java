package com.iso.claimsearch.casemanager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iso.claimsearch.casemanager.dao.CaseDao;
import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.model.MyCase;

/**
 * This class is responsible for getting case related information.
 * 
 * @author i90845
 */

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CaseServiceImpl implements CaseService {
  private static final Logger LOGGER = Logger.getLogger(CaseServiceImpl.class);

  @Autowired
  private CaseDao caseDao;

  /**
   * Loads list of my cases through DAO layer.
   * 
   * @return List of My Cases.
   */
  @Override
  public List<MyCase> getMyOpenCases(String userId) {
    return caseDao.getMyOpenCases(userId, Constants.OPEN_CASE_STATUS);
  }
}
