package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IzvestajPostojiAdvice {

	  @ResponseBody
	  @ExceptionHandler(IzvestajPostojiException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String IzvestajPostojiHandler(IzvestajPostojiException ex) {
		  return ex.getMessage();
	  }
}
