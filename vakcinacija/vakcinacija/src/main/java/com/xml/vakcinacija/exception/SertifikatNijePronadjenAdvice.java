package com.xml.vakcinacija.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SertifikatNijePronadjenAdvice {
    @ResponseBody
    @ExceptionHandler(SertifikatNijePronadjenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String SertifikatNijePronadjenHandler(SertifikatNijePronadjenException ex) {
        return ex.getMessage();
    }
}
