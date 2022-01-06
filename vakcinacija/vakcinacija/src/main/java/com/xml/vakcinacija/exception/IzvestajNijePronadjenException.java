package com.xml.vakcinacija.exception;

public class IzvestajNijePronadjenException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public IzvestajNijePronadjenException(String odDatum, String doDatum) {
	    super("Nije pronadjen izvestaj u periodu " + odDatum + " - " + doDatum + "!");
	}
}
