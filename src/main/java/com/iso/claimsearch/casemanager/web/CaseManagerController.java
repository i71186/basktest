package com.iso.claimsearch.casemanager.web;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.model.ActivityResponse;
import com.iso.claimsearch.casemanager.model.CaseDetails;
import com.iso.claimsearch.casemanager.model.CaseEntity;
import com.iso.claimsearch.casemanager.model.CaseEntityResponse;
import com.iso.claimsearch.casemanager.model.ClaimDetails;
import com.iso.claimsearch.casemanager.model.ClaimReferral;
import com.iso.claimsearch.casemanager.model.MyCase;
import com.iso.claimsearch.casemanager.model.MyCasesResponse;
import com.iso.claimsearch.casemanager.model.Referral;
import com.iso.claimsearch.casemanager.model.ReferralEntity;
import com.iso.claimsearch.casemanager.model.ReferralFormRequest;
import com.iso.claimsearch.casemanager.model.ReferralRequest;
import com.iso.claimsearch.casemanager.model.ReferralResponse;
import com.iso.claimsearch.casemanager.service.ActivityService;
import com.iso.claimsearch.casemanager.service.CaseEntityService;
import com.iso.claimsearch.casemanager.service.CaseService;
import com.iso.claimsearch.casemanager.service.ReferralEntityService;
import com.iso.claimsearch.casemanager.service.ReferralService;
import com.iso.claimsearch.casemanager.util.ReferralUtil;

/**
 * This is the class responsible for providing a entry-point to get Case Manager details...
 * 
 * @author i90845
 */

@RestController
@RequestMapping("/1.0")
public class CaseManagerController {

  @Autowired
  private CaseService caseService;

  @Autowired
  private CaseEntityService caseEntityService;

  @Autowired
  private Validator validator;

  @Autowired
  private ActivityService activityService;

  @Autowired
  private ReferralService referralService;

  @Autowired
  private ReferralEntityService referralentityService;

  private static Logger LOG = Logger.getLogger(CaseManagerController.class);

  /**
   * Uses the user id and returns list of my open cases.
   * 
   * @param userId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/mycases", method = RequestMethod.GET,
      headers = "Accept=application/json")
  @ResponseBody
  public MyCasesResponse getMyCases() throws Exception {
    MyCasesResponse myCasesResponse = new MyCasesResponse();
    if (hasRole(Constants.ROLE_CASE_MANAGER_USER)) {
      String userId = getUserId();
      System.out.println(userId);
      List<MyCase> myOpenCases = caseService.getMyOpenCases(userId.toUpperCase());

      myCasesResponse.setMyOpenCases(myOpenCases);
      myCasesResponse.setStatus((null != myCasesResponse && !myCasesResponse.getMyOpenCases()
          .isEmpty()) ? Constants.SUCCESS : Constants.NO_OPEN_CASES);
    } else {
      myCasesResponse.setStatus(Constants.ACCESS_DENIED);
    }
    return myCasesResponse;
  }

  /**
   * Uses case id and returns list of case entities.
   * 
   * @param caseId
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/caseentities/{caseId}", method = RequestMethod.GET,
      headers = "Accept=application/json")
  @ResponseBody
  public CaseEntityResponse getCaseEntities(@PathVariable String caseId) throws Exception {

    CaseEntityResponse myCaseEntitiesResponse = new CaseEntityResponse();

    if (hasRole(Constants.ROLE_CASE_MANAGER_USER)) {
      List<CaseEntity> caseEntities =
          caseEntityService.getCaseEntities(caseId, Constants.COMPANY_ID);
      CaseDetails caseDetails = caseEntityService.getCaseDetails(caseId);

      if (caseDetails != null) {
        myCaseEntitiesResponse.setCaseNumber(caseDetails.getCaseNumber());
        myCaseEntitiesResponse.setLastUpdated(caseDetails.getLastUpdated());
        myCaseEntitiesResponse.setCity(caseDetails.getCity());
        myCaseEntitiesResponse.setState(caseDetails.getState());
      }

      myCaseEntitiesResponse.setCaseEntities(caseEntities);
      myCaseEntitiesResponse.setStatus((null != myCaseEntitiesResponse && !myCaseEntitiesResponse
          .getCaseEntities().isEmpty()) ? Constants.SUCCESS : Constants.NO_CASE_ENTITIES);
    } else {
      myCaseEntitiesResponse.setStatus(Constants.ACCESS_DENIED);
    }
    return myCaseEntitiesResponse;
  }

  /**
   * Uses title, description and activity is created.
   * 
   * @param urlString
   * @param token
   * @param urlParameters
   * 
   * @return
   * @throws Exception
   */

  @RequestMapping(value = "/addactivity", method = RequestMethod.POST)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ResponseBody
  public ActivityResponse addActivity(@RequestBody Activity activity, HttpServletRequest request,
      BindingResult result) throws BindException {
    ActivityResponse activityResponse = new ActivityResponse();

    if (hasRole(Constants.ROLE_CASE_MANAGER_USER)) {
      activity = populateActivityObject(activity);
      validateUserInput(activity, result);
      activity = activityService.addActivity(activity);

      if (activity.getActivityId() != null) {
        activityResponse.setStatusCode(Constants.ACT_STATUS_COMPLETE);
        activityResponse.setStatusDesc(Constants.SUCCESS);
      } else {
        activityResponse.setStatusCode(Constants.ACT_STATUS_FAIL);
        activityResponse.setStatusDesc(Constants.ACT_STATUS_FAIL_DESC);
      }

    } else {
      activityResponse.setStatusCode(Constants.ACCESS_DENIED_CODE);
      activityResponse.setStatusDesc(Constants.ACCESS_DENIED);
    }

    return activityResponse;

  }

  @RequestMapping(value = "/uploadattachments", method = RequestMethod.POST)
  @ResponseBody
  public ActivityResponse uploadattachments(Activity activity, MultipartHttpServletRequest request,
      BindingResult result) throws BindException {
    LOG.info("uploadattachments method execution begin");
    ActivityResponse activityResponse = new ActivityResponse();

    activity = populateActivityObject(activity);
    LOG.debug("Populated acitivity object{}" + activity.toString());
    validateUserInput(activity, result);
    activity = activityService.addActivity(activity);

    if (null != request.getMultiFileMap() && request.getMultiFileMap().size() > 0
        && activity.getActivityId() != null) {
      activityService.saveAttachements(request.getMultiFileMap(), activity.getActivityId(),
          activity.getAppId());
    }

    if (activity.getActivityId() != null) {
      activityResponse.setStatusCode(Constants.ACT_STATUS_COMPLETE);
      activityResponse.setStatusDesc(Constants.SUCCESS);
    } else {
      activityResponse.setStatusCode(Constants.ACT_STATUS_FAIL);
      activityResponse.setStatusDesc(Constants.ACT_STATUS_FAIL_DESC);
    }
    LOG.info("uploadattachments () method execution completed");

    return activityResponse;

  }

  @RequestMapping(value = "/createreferral", method = RequestMethod.POST)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ResponseBody
  public ReferralResponse createReferral(@RequestBody ReferralRequest referralRequest,
      HttpServletRequest request, BindingResult result) throws BindException {

    ReferralResponse referralResponse = new ReferralResponse();

    if (referralRequest != null) {
      if (StringUtils.isEmpty(referralRequest.getCompanyId())) {
        referralRequest.setCompanyId(referralService.getCompanyByClaimId(referralRequest
            .getClaimId()));
      }

      if (StringUtils.isNotEmpty(referralRequest.getCompanyId())) {
        if (referralRequest.getCompanyId().equalsIgnoreCase(Constants.CUSTOMER_ID_A011)) {
          referralRequest.setUserId("FHY1Q");
        } else if (referralRequest.getCompanyId().equalsIgnoreCase(Constants.CUSTOMER_ID_X813)) {
          referralRequest.setUserId("IEX8E");
        }
      } else {
        referralRequest.setUserId("FHY1Q");
        referralRequest.setCompanyId(Constants.CUSTOMER_ID_A011);
      }
    }

    ClaimDetails claimDetails = new ClaimDetails();
    ClaimReferral claimReferral =
        referralService.getClaimReferral(referralRequest.getClaimId(),
            referralRequest.getCompanyId());
    if (claimReferral != null) {
      List<ReferralEntity> referralEntityList =
          referralentityService.getReferralEntities(referralRequest.getClaimId());
      List entitites =
          referralentityService.getEntities(referralEntityList, referralRequest.getCompanyId());
      claimDetails.setClaimReferral(claimReferral);
      claimDetails.setReferralEntityList(referralEntityList);
      Referral referral = new Referral();
      referral.setReferralEntities(entitites);
      ReferralUtil.populateReferralObject(referral, claimReferral);
      ReferralUtil.populateAssurantDefaultValues(referral, referralRequest.getUserId());

      String status = referralService.addReferral(referral);
      referralResponse.setStatus(status);
      referralResponse.setDescription("Referral created Successfully.");
    } else {

      referralResponse.setDescription(Constants.NO_CLAIM_REFERRAL);
      referralResponse.setStatus(Constants.FAILED);
    }

    return referralResponse;
  }

  @RequestMapping(value = "/generatereferral", method = RequestMethod.POST)
  @ResponseBody
  public ReferralResponse generateReferral(
      @RequestBody @Valid ReferralFormRequest referralFormRequest, HttpServletRequest request,
      BindingResult result) throws BindException {

    ReferralResponse referralResponse = new ReferralResponse();
    ReferralTransformer transformer = new ReferralTransformer();
    Referral referral = transformer.transform(referralFormRequest);
    if (referral.getReferralEntities().size() > 0) {
      for (ReferralEntity entity : referral.getReferralEntities()) {
        if ((entity.getRole() == null || entity.getRole().isEmpty())
            || (entity.getFirstName() == null || entity.getFirstName().isEmpty())
            || (entity.getLastName() == null || entity.getLastName().isEmpty())) {
          referralResponse.setStatus(Constants.INVALID_TXT);
          referralResponse.setDescription("A field is missing in Referral Entity");
          return referralResponse;
        }
      }
    }
    String status = referralService.addReferralSCIF(referral);
    referralResponse.setStatus(status);
    referralResponse
        .setDescription(Constants.SUCCESS.equals(status) ? "Referral created Successfully."
            : "Referral not created.");
    return referralResponse;
  }

  private void validateUserInput(Activity activity, BindingResult result) throws BindException {
    validator.validate(activity, result);

    if (result.hasErrors()) {
      throw new BindException(result);
    }
  }

  private Activity populateActivityObject(Activity activity) {

    String userId = getUserId();
    System.out.println("User in add activity = " + userId);
    LOG.debug("User in add activity = " + userId);
    // Timestamp rightNow = new Timestamp(new Date().getTime());

    activity.setActivityAssignedUser(userId);
    // activity.setActivityCreatedDate(rightNow);
    // activity.setActivityDate(rightNow);
    // activity.setActivityDueDate(DateUtil.getFormattedDate(rightNow));
    activity.setActivityEnteredUser(userId);
    activity.setActivityStatus(Constants.ACTIVITY_CLOSED_STATUS);
    activity.setUserId(userId);
    return activity;
  }

  private String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Assert.notNull(authentication);
    return authentication.getPrincipal().toString();
  }

  private boolean hasRole(String role) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getAuthorities() == null) {
      return false;
    }

    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().equals(role)) {
        return true;
      }
    }

    return false;
  }

  protected static void trustAllCerts() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {}

      public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    }};
    SSLContext sc = SSLContext.getInstance("TLSv1.2");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }
}
