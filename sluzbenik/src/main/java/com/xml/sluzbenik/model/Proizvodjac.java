package com.xml.sluzbenik.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Proizvodjac {
	@XmlEnumValue("Pfizer-BioNTech")
	PFIZER("Pfizer-BioNTech"),
	
    @XmlEnumValue("Sputnik V (Gamaleya истраживачки центар)")
    SPUTNIK("Sputnik V (Gamaleya истраживачки центар)"),
    
	@XmlEnumValue("Sinopharm")
    SINOPHARM("Sinopharm"),
	
	@XmlEnumValue("AstraZeneca")
    ASTRA_ZENECA("AstraZeneca"),
    
	@XmlEnumValue("Moderna")
    MODERNA("Moderna");
	
    private final String proizvodjac;

	private Proizvodjac(String proizvodjac) { this.proizvodjac = proizvodjac; }
	
	@Override
	public String toString() {
		return this.proizvodjac;
	}
	
	public static Proizvodjac fromString(String text) {
        for (Proizvodjac p : Proizvodjac.values()) {
            if (p.toString().equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
	}
}
