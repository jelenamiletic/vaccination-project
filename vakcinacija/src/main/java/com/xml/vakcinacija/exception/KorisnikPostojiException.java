package com.xml.vakcinacija.exception;

public class KorisnikPostojiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public KorisnikPostojiException(String email) {
	    super("Korisnik sa ovim email-om (" + email + ") " + "vec postoji!");
	}
}
