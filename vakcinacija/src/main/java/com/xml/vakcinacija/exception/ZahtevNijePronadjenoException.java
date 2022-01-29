package com.xml.vakcinacija.exception;

public class ZahtevNijePronadjenoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ZahtevNijePronadjenoException(String jmbg) {
	    super("Zahtev sa ovim JMBG-om (" + jmbg + ") " + "nije pronadjeno!");
	}
}
