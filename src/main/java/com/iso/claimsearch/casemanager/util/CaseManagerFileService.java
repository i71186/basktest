package com.iso.claimsearch.casemanager.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.log4j.Logger;

import com.iso.claimsearch.casemanager.exception.CaseManagerNotFoundException;
import com.iso.service.fileupload.FileService;
import com.iso.service.fileupload.Parameter;
import com.iso.service.fileupload.Token;
import com.iso.service.fileupload.client.ServerResponse;
import com.iso.service.fileupload.client.factory.FileServiceFactory;

public class CaseManagerFileService {

  private static final Logger LOG = Logger.getLogger(CaseManagerFileService.class);

  /**
   * @param fileName
   * @param imageBytes
   * @return
   */
  public static ServerResponse scanInputFile(String fileName, byte[] imageBytes, String appId) {
    ServerResponse response = null;
    Token token = new Token(appId); // this is how it knows what plugin to call
    FileService fileService = FileServiceFactory.newInstance(token);

    LOG.debug("Scanning the input image : " + fileName);
    List<Parameter> parameters = new ArrayList<Parameter>();
    ByteArrayPartSource byteArrPartSource = new ByteArrayPartSource(fileName, imageBytes);
    parameters.add(new Parameter("req.type", "2"));
    parameters.add(new Parameter("first_file", byteArrPartSource));
    LOG.debug("The token for Case Manager file service is : " + fileService.getToken());
    try {
      response = fileService.execute("put", parameters);
      LOG.debug("The status of scanning is : " + response.getStatus());
      LOG.debug("The transaction ID of scanning is : " + response.getTransactionUid());
      LOG.debug("The file scanning is for filename " + fileName + " and saved to the server.");

      if (!response.isSuccessful()) {
        String excepString = "Exception: File scanning failed for the input file : " + fileName;
        LOG.error(" Error: " + excepString + " and status returned is :" + response.getStatus());
        throw new CaseManagerNotFoundException(excepString + " and the Status returned is >>"
            + response.getStatus());
      }
    } catch (Exception exceptionObj) {
      LOG.error("Error occured while scanning the input file :" + exceptionObj);
      throw new CaseManagerNotFoundException("Exception occured while scanning the input file :"
          + exceptionObj);
    }
    return response;
  }
}
