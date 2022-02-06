package com.xml.vakcinacija.exception;

public class KorisnikPostojiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public KorisnikPostojiException() {
	    super("Korisnik sa istim JMBG-om ili email-om vec postoji u sistemu!");
	}
}
