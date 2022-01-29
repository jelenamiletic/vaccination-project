package com.xml.vakcinacija.exception;

public class SaglasnostNijePronadjenaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SaglasnostNijePronadjenaException(String jmbg) {
	    super("Saglasnost sa ovim JMBG-om (" + jmbg + ") " + "nije pronadjena!");
	}
}
