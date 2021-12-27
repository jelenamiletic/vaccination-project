package com.xml.vakcinacija.exception;

public class PotvrdaNijePronadjenoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PotvrdaNijePronadjenoException(String jmbg) {
	    super("Potvrda sa ovim JMBG-om (" + jmbg + ") " + "nije pronadjeno!");
	}
}
