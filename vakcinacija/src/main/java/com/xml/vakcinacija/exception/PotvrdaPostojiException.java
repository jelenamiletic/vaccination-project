package com.xml.vakcinacija.exception;

public class PotvrdaPostojiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PotvrdaPostojiException(String jmbg) {
	    super("Potvrda sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
