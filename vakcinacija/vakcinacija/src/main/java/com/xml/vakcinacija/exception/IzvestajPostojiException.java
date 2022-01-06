package com.xml.vakcinacija.exception;

public class IzvestajPostojiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IzvestajPostojiException(String odDatum, String doDatum) {
	    super("Izvestaj u okviru perioda (" + odDatum + " - " + doDatum + ") vec postoji!");
	}
}
