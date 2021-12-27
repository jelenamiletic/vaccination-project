package com.xml.vakcinacija.exception;

public class PotvrdaPostojiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PotvrdaPostojiException(String jmbg) {
	    super("Interesovanje sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
