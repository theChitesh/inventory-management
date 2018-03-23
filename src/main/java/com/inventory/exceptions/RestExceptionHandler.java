package com.inventory.exceptions;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ResponseEntity<String> handleAccessDeniedException(final Throwable e) {
    // TODO : have a error dto to return more details for failure
    LOGGER.info("Internal error accured", e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity<String> handleAccessDeniedException(final AccessDeniedException e) {
    // TODO : have a error dto to return more details for failure
    LOGGER.info("Access denied", e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(StockException.class)
  @ResponseBody
  public ResponseEntity<String> handleAccessDeniedException(final StockException e) {
	  
	 LOGGER.info("HTTP 400 Bad Request", e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<String> handleAccessDeniedException(final MethodArgumentNotValidException e) {

    final String details = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(x -> x.getDefaultMessage() + " : " + x.getRejectedValue())
        .collect(Collectors.joining("; "));
    LOGGER.info("HTTP 400 Bad Request", e);
    return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
  }
}
