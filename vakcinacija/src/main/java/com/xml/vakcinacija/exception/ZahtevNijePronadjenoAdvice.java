package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ZahtevNijePronadjenoAdvice {

	  @ResponseBody
	  @ExceptionHandler(InteresovanjeNijePronadjenoException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String ZahtevNijePronadjenoHandler(ZahtevNijePronadjenoException ex) {
		  return ex.getMessage();
	  }
}
