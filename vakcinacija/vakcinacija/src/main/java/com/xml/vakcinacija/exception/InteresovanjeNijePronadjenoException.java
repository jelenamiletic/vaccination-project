package com.xml.vakcinacija.exception;

public class InteresovanjeNijePronadjenoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InteresovanjeNijePronadjenoException(String jmbg) {
	    super("Interesovanje sa ovim JMBG-om (" + jmbg + ") " + "nije pronadjeno!");
	}
}
