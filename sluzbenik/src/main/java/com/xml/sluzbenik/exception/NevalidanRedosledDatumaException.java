package com.xml.sluzbenik.exception;

public class NevalidanRedosledDatumaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	

	public NevalidanRedosledDatumaException(String odDatum, String doDatum) {
        super("Od datum ( " + odDatum + " ) ne moze biti posle do datuma ( " + doDatum + " )!");
    }
}
