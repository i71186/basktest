package com.iso.claimsearch.casemanager.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iso.claimsearch.casemanager.exception.CaseManagerNotFoundException;

/**
 * A controller advice that handles various exceptions for all the restful services.
 * 
 * @author i90845
 * 
 */
@ControllerAdvice
public class RestExceptionHandler {

  private static final String GENERIC_ERROR_MESSAGE =
      "An unknown error has occurred. Please contact customer support.";
  private static final Logger LOGGER = LogManager.getLogger(RestExceptionHandler.class);
  private static final String STATUS = "ERROR";

  /**
   * This method handles exceptions while binding the form fields to the POJO
   * 
   * @param bindException exception that occurred during the binding of request parameters
   * @return CaseManagerValidationError Object.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler({BindException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public CaseManagerValidationError handleValidationException(BindException bindException) {
    return getValidationErrorsFromValidation(bindException.getBindingResult());
  }

  /**
   * This method handles exceptions while validating the request body
   * 
   * @param methodArgInvalidException The MethodArgumentNotValidException that occurs during the
   *        binding of request parameters
   * @return CaseManagerValidationError Object.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public CaseManagerValidationError handleValidationException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    return getValidationErrorsFromValidation(methodArgumentNotValidException.getBindingResult());
  }

  /**
   * This method handles HttpMessageNotReadableException, like invalid JSON format.
   * 
   * @param messageNotReadableException The HttpMessageNotReadableException that occurs during the
   *        binding of request parameters
   * @return CaseManagerValidationError Object.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler({HttpMessageNotReadableException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public CaseManagerValidationError handleValidationException(
      HttpMessageNotReadableException messageNotReadableException) {
    CaseManagerValidationError validationError = new CaseManagerValidationError();
    validationError.setStatus(STATUS);
    validationError.addFieldError("Invalid Input", messageNotReadableException.getMessage());
    return validationError;
  }

  /**
   * This method handles exceptions if its not handled by any of the above methods
   * 
   * @param exception
   */
  @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Object handleException(Exception exception) {
    LOGGER.error(exception.getMessage(), exception);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("error", exception instanceof CaseManagerNotFoundException ? exception.getMessage()
        : GENERIC_ERROR_MESSAGE);
    return result;
  }

  private CaseManagerValidationError getValidationErrorsFromValidation(BindingResult bindResult) {
    CaseManagerValidationError validationError = new CaseManagerValidationError();
    for (FieldError fldErr : bindResult.getFieldErrors()) {
      validationError.addFieldError(fldErr.getField(), fldErr.getDefaultMessage());
    }
    for (ObjectError objErr : bindResult.getGlobalErrors()) {
      validationError.addFieldError(objErr.getObjectName(), objErr.getDefaultMessage());
    }
    validationError.setStatus(STATUS);
    return validationError;
  }

}
