package com.iso.claimsearch.casemanager.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.iso.claimsearch.casemanager.dao.ActivityDao;
import com.iso.claimsearch.casemanager.dao.Constants;
import com.iso.claimsearch.casemanager.exception.CaseManagerNotFoundException;
import com.iso.claimsearch.casemanager.model.Activity;
import com.iso.claimsearch.casemanager.model.ActivityAttachment;
import com.iso.claimsearch.casemanager.model.ActivityResponse;
import com.iso.claimsearch.casemanager.util.CaseManagerFileService;
import com.iso.service.fileupload.RemoteFile;
import com.iso.service.fileupload.client.ServerResponse;

/**
 * This class is responsible for Activity related activities.
 * 
 * @author i90845
 */

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityServiceImpl implements ActivityService {

  private static final Logger LOG = Logger.getLogger(ActivityServiceImpl.class);

  @Autowired
  private ActivityDao activityDao;

  /*
   * @Autowired private CaseManagerFileService fileService;
   */

  private String isFileServiceNeeded;
  private String imageRepositoryLocation;

  @Value("${siucfileservice.props}")
  private String siucFileserviceProp;

  /**
   * Storing the Activity Information via DAO layer.
   * 
   * @param Activity activity Information about the activity
   * @return String Status of Adding activity.
   */

  @Override
  @Transactional
  public Activity addActivity(Activity activity) {
    return activityDao.addActivity(activity);

  }

  /**
   * @param viInfo
   * @return
   */
  public ActivityResponse saveAttachements(MultiValueMap<String, MultipartFile> attachementMap,
      Long activityId, String appId) {
    LOG.info("saveAttachements() method execution begin");
    LOG.info("Before fileserviceProp : " + siucFileserviceProp);
    System.setProperty("fileservice.props", siucFileserviceProp);
    LOG.info(" After fileserviceProp : " + System.getProperty("fileservice.props"));

    Iterator<Entry<String, List<MultipartFile>>> iter = attachementMap.entrySet().iterator();
    List<ServerResponse> responses = new ArrayList<ServerResponse>();

    while (iter.hasNext()) {
      Entry<String, List<MultipartFile>> fileEntry = iter.next();
      List<MultipartFile> fileList = attachementMap.get(fileEntry.getKey());
      Assert.notEmpty(fileList, "Request doesnt contain any images.");
      LOG.debug(" The count of the images uploaded : " + fileList.size());
      processMultipartFile(fileList, appId, responses);
    }// while

    if (responses.size() > 0) {

      for (int i = 0; i < responses.size(); i++) {

        ActivityAttachment activityAttachment = new ActivityAttachment();
        activityAttachment =
            populateActivityAttachment(activityAttachment, responses.get(i), appId);
        activityAttachment.setActivityId(activityId);
        activityAttachment.setSequenceNo(i);

        activityDao.addAttachmentInfo(activityAttachment);

      }
    }


    ActivityResponse activityResponse = new ActivityResponse();

    activityResponse.setStatus(Constants.SUCCESS);
    LOG.info("saveAttachements() method execution completed");
    return activityResponse;
  }// saveAttachements

  public ActivityAttachment populateActivityAttachment(ActivityAttachment activityAttachment,
      ServerResponse response, String appId) {

    // response.getRemoteFiles()
    LinkedList list = (LinkedList) response.getRemoteFiles();
    RemoteFile temp = (RemoteFile) list.get(0);

    activityAttachment.setFileName(temp.getFileName());

    String filePath = "";
    int indexPosition = temp.getPath().lastIndexOf(appId);
    if (indexPosition != -1) {
      filePath = temp.getPath().substring(indexPosition + appId.length(), temp.getPath().length());
    }

    activityAttachment.setFilePath(filePath);
    activityAttachment.setFileSize(temp.getFileSize());
    activityAttachment.setFileUid(temp.getUid());

    return activityAttachment;
  }

  /***
   * @param fileList
   * @param transId
   * @param imageMap
   */
  private List<ServerResponse> processMultipartFile(List<MultipartFile> fileList, String appId,
      List<ServerResponse> responses) {
    LOG.info("processMultipartFile() method execution begin");
    String fileName = null;
    String originalFileName = null;
    String fileExtension = null;


    for (MultipartFile mpf : fileList) {
      if (!mpf.isEmpty()) {
        FileOutputStream fos = null;
        try {
          byte[] bytes = mpf.getBytes();
          LOG.debug(" The file name:" + mpf.getName());
          originalFileName = mpf.getOriginalFilename();
          LOG.debug(" The original file name:" + originalFileName);
          fileExtension = checkValidityOfFileExtension(originalFileName);

          if ((Constants.INVALID_TXT).equalsIgnoreCase(fileExtension)) {
            continue;
          }// if
           // fileName = mpf.getName() + fileExtension;
          fileName = originalFileName;
          LOG.debug("Saving " + fileName + " to the server.");
          Assert.notNull(fileName, "File Name cannot be null");

          ServerResponse response = CaseManagerFileService.scanInputFile(fileName, bytes, appId);
          responses.add(response);
        }// try
        catch (FileNotFoundException fileNotFoundException) {
          LOG.error("Error occured while saving an image to server: " + fileNotFoundException);
          throw new CaseManagerNotFoundException(
              "Exception occured while saving an image to file: " + fileNotFoundException);
        } catch (IOException ioException) {
          LOG.error("Error occured while saving image : " + ioException);
          throw new CaseManagerNotFoundException("Exception occured while saving image : "
              + ioException);
        } finally {
          if (fos != null) {
            try {
              fos.close();
            } catch (IOException ioException) {
              LOG.error("Error while saving an image: " + ioException);
              throw new CaseManagerNotFoundException("Exception while saving an image: "
                  + ioException);
            }// catch
          }// if
        }// finally
      }// if
    }// for
    LOG.info("processMultipartFile() method execution completed");
    return responses;
  }// processMultipartFile

  /***
   * 
   * @param originalFileName
   * @return
   */
  private String checkValidityOfFileExtension(String fileName) {
    String fileExtension = null;
    int indexPosition = 0;
    indexPosition = fileName.lastIndexOf('.');
    if (indexPosition != -1) {
      fileExtension = fileName.substring(indexPosition, fileName.length());
      /*
       * if (!(fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".png") ||
       * fileExtension .equalsIgnoreCase(".gif"))) { fileExtension = Constants.INVALID_TXT; }
       */// if
    }// if
    return fileExtension;
  }// checkValidityOfFileExtension

  /*
   * public CaseManagerFileService getFileService() { return fileService; }
   * 
   * public void setFileService(CaseManagerFileService fileService) { this.fileService =
   * fileService; }
   */

  public String getIsFileServiceNeeded() {
    return isFileServiceNeeded;
  }

  public void setIsFileServiceNeeded(String isFileServiceNeeded) {
    this.isFileServiceNeeded = isFileServiceNeeded;
  }

  public String getImageRepositoryLocation() {
    return imageRepositoryLocation;
  }

  public void setImageRepositoryLocation(String imageRepositoryLocation) {
    this.imageRepositoryLocation = imageRepositoryLocation;
  }

}
