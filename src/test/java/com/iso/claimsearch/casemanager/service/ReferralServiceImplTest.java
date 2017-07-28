package com.iso.claimsearch.casemanager.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.iso.claimsearch.casemanager.dao.ReferralDao;
import com.iso.claimsearch.casemanager.model.Referral;

/**
 * @author i95196
 * 
 */
public class ReferralServiceImplTest {

  private ReferralServiceImpl referralService;

  @Mock
  private ReferralDao referralDao;

  @Before
  public void setUp() throws Exception {
    referralService = new ReferralServiceImpl();
    MockitoAnnotations.initMocks(this);
    referralService.setReferralDao(referralDao);
  }


  @Test
  public void testAddReferralSCIF() throws Exception {
    String companyId = "S138";
    Referral referral = new Referral();
    referral.setCompanyId(companyId);

    Mockito.when(referralDao.addReferral(referral)).thenReturn(null);
    Mockito.when(referralDao.getUnivValueByCompIdAndType(Mockito.anyString(), Mockito.anyString()))
        .thenReturn("CDSQ");
    Mockito.when(referralDao.generateReferralNumberFromCustomerSequence(Mockito.anyString()))
        .thenReturn("MockedreferralNumber");

    referralService.addReferralSCIF(referral);

    assertEquals("MockedreferralNumber", referral.getReferralNumber());
    Mockito.verify(referralDao).addReferralSCIF(referral);
    Mockito.verify(referralDao).generateReferralNumberFromCustomerSequence(companyId);
    Mockito.verify(referralDao, Mockito.never()).getCountByClaimNumber(Mockito.anyString(),
        Mockito.anyString());
  }

  @Test
  public void testAddReferralAssurant() throws Exception {
    String companyId = "A011";
    Referral referral = new Referral();
    referral.setCompanyId(companyId);
    referral.setClaimNumber("20");

    Mockito.when(referralDao.addReferral(referral)).thenReturn(null);
    Mockito.when(referralDao.getUnivValueByCompIdAndType(Mockito.anyString(), Mockito.anyString()))
        .thenReturn("CNUN");
    Mockito.when(referralDao.getCountByClaimNumber(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(1);

    referralService.addReferral(referral);
    Mockito.verify(referralDao).addReferral(referral);
    Mockito.verify(referralDao).getCountByClaimNumber(Mockito.anyString(), Mockito.anyString());
    Mockito.verify(referralDao, Mockito.never()).generateReferralNumberFromCustomerSequence(
        Mockito.anyString());
  }
}
