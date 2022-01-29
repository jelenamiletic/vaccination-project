package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InteresovanjePostojiAdvice {

	  @ResponseBody
	  @ExceptionHandler(InteresovanjePostojiException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String InteresovanjePostojiHandler(InteresovanjePostojiException ex) {
		  return ex.getMessage();
	  }
}
