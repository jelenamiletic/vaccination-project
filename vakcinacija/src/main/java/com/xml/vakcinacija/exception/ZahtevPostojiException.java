package com.xml.vakcinacija.exception;

public class ZahtevPostojiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ZahtevPostojiException(String jmbg) {
	    super("Zahtev sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
