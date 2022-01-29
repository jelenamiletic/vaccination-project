package com.xml.vakcinacija.exception;

public class SertifikatPostojiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SertifikatPostojiException(String jmbg) {
	    super("Sertifikat sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
