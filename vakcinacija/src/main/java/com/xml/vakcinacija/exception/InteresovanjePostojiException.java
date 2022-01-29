package com.xml.vakcinacija.exception;

public class InteresovanjePostojiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InteresovanjePostojiException(String jmbg) {
	    super("Interesovanje sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
