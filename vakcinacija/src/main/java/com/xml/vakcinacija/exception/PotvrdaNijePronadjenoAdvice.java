package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PotvrdaNijePronadjenoAdvice {

	  @ResponseBody
	  @ExceptionHandler(PotvrdaNijePronadjenoException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String PotvrdaNijePronadjenoHandler(PotvrdaNijePronadjenoException ex) {
		  return ex.getMessage();
	  }
}
