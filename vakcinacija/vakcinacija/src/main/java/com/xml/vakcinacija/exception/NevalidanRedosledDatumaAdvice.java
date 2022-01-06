package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NevalidanRedosledDatumaAdvice {

	  @ResponseBody
	  @ExceptionHandler(NevalidanRedosledDatumaException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String NevalidanRedosledDatumaHandler(NevalidanRedosledDatumaException ex) {
		  return ex.getMessage();
	  }
}
