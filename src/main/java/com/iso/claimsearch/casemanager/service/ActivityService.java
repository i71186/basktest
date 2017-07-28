package com.iso.claimsearch.casemanager.service;


import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.model.ActivityResponse;

/**
 * Implementing classes are responsible for invoking appropriate method calls through DAO layer.
 * 
 * @author i90845
 */

public interface ActivityService {

  public Activity addActivity(Activity activity);

  public ActivityResponse saveAttachements(MultiValueMap<String, MultipartFile> attachementMap,
      Long activityId, String appId);

}
