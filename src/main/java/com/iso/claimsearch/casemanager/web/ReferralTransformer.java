package com.iso.claimsearch.casemanager.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iso.claimsearch.casemanager.model.Referral;
import com.iso.claimsearch.casemanager.model.ReferralFormRequest;

public class ReferralTransformer {

  private static final String COMPANY_SCIF = "S138";
  private static final String DEFAULT_DATE = "01/01/9999";

  public Referral transform(ReferralFormRequest referralForm) {
    Referral referral = new Referral();
    referral.setCompanyId(referralForm.getCompanyId() == null ? COMPANY_SCIF : referralForm
        .getCompanyId());
    referral.setLossType(referralForm.getLossType());

    referral.setLossSubType(referralForm.getLossSubType());
    referral.setPolicyNumber(referralForm.getPolicyNumber());
    referral.setClaimNumber(referralForm.getClaimNumber());

    referral.setDateOfLoss(referralForm.getDateOfLoss() != null ? referralForm.getDateOfLoss()
        : getDefaultDate());
    referral.setSuspicionReason(referralForm.getReferralReason()); // format-0009,0004,etc
    referral.setReasonText(referralForm.getReferralReasonText());
    referral.setCreatedBy(referralForm.getUserId());
    referral.setReferralStatus("0001");
    referral.setManagerQueue(referralForm.getUserId());
    referral.setSource(referralForm.getUserId());
    referral.setReferralType("0001");
    referral.setGroup(referralForm.getGroup() != null ? referralForm.getGroup() : "");
    referral.setBusinessUnit(referralForm.getSourceLocation() != null ? referralForm
        .getSourceLocation() : "");
    referral
        .setPolicyYear(referralForm.getPolicyYear() != null ? referralForm.getPolicyYear() : "");
    Date today = new Date();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    referral.setDateAssigned(today);
    referral.setDateReceived(timestamp);

    referral.setAgentName(referralForm.getReporterFirstName() + " "
        + referralForm.getReporterLastName());
    referral.setAgentEmail(referralForm.getReporterEmail());

    referral.setReferralEntities(referralForm.getReferralEntities());

    return referral;
  }


  public Date getDefaultDate() {

    SimpleDateFormat in = new SimpleDateFormat("dd/MM/yyyy");

    String dt = DEFAULT_DATE; // DD/MM/YYYY
    try {
      Date date = in.parse(dt);
      return date;
    } catch (Exception e) {
      System.out.println("Exception is :" + e);
    }
    return null;
  }
}
