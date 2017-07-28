package com.iso.claimsearch.casemanager.util;

import java.sql.Timestamp;
import java.util.Date;

import com.iso.claimsearch.casemanager.model.ClaimReferral;
import com.iso.claimsearch.casemanager.model.Referral;

public class ReferralUtil {

  public static final int REFERRAL_NO_LENGTH = 35;

  public static final String CLAIM_CODE = "0001";

  public static Referral populateReferralObject(Referral referral, ClaimReferral claimReferral) {

    Timestamp rightNow = new Timestamp(new Date().getTime());

    referral.setClaimNumber(claimReferral.getClaimNumber());
    referral.setDateOfLoss(claimReferral.getDateOfLoss());
    referral.setPolicyNumber(claimReferral.getPolicyNumber());
    referral.setLolAddress(claimReferral.getLolAddress());
    referral.setCity(claimReferral.getLolCity());
    referral.setLolState(claimReferral.getLolState());
    referral.setState(claimReferral.getLolState());
    referral.setAllClaimId(claimReferral.getAllClaimId());
    referral.setCompanyId(claimReferral.getCompanyId());
    referral.setDateAssigned(DateUtil.getFormattedDate(rightNow));
    referral.setDateReceived(rightNow);


    return referral;

  }


  public static Referral populateAssurantDefaultValues(Referral referral, String userId) {

    referral.setLossType("0078");// 'ClaimDirector Review'
    referral.setLossSubType("4527");// 'ClaimDirector Review'
    referral.setReasonText("ClaimDirector Referral");
    referral.setBusinessUnit("");// TODO need
    referral.setCity("#ClaimDirector");
    referral.setManagerQueue(userId);
    referral.setSource(userId);
    referral.setReferralType("0001");
    referral.setCreatedBy(userId);
    referral.setReferralStatus("0001");// unassigned
    referral.setSuspicionReason("0009");// A011 business unit goes in suspicionReason

    return referral;
  }

  public static String generateCNUNClaimRefferalNumber(int refCnt, String referralNumber) {
    String genNum = referralNumber;
    if (refCnt > 0) {
      genNum = genNum + "-" + (refCnt + 1);
      if (genNum.length() > REFERRAL_NO_LENGTH) {
        int index = genNum.lastIndexOf("-");
        genNum = genNum.substring(0, index - (genNum.length() - REFERRAL_NO_LENGTH));
        genNum = genNum + "-" + (refCnt + 1);
      }
    }
    return genNum;
  }
}
