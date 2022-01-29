package com.xml.vakcinacija.exception;

public class SaglasnostPostojiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SaglasnostPostojiException(String jmbg) {
	    super("Saglasnost sa ovim JMBG-om (" + jmbg + ") " + "vec postoji!");
	}
}
