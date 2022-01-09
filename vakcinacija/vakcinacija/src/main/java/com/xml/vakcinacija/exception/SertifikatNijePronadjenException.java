package com.xml.vakcinacija.exception;

public class SertifikatNijePronadjenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SertifikatNijePronadjenException(String jmbg) {
        super("Sertifika sa ovim JMBG-om (" + jmbg + ") " + "nije pronadjen!");
    }
}
