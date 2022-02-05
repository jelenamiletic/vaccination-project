package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class KorisnikPostojiAdvice {

	@ResponseBody
	@ExceptionHandler(KorisnikPostojiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String KorisnikPostojiHandler(KorisnikPostojiException ex) {
		return ex.getMessage();
	}
}
