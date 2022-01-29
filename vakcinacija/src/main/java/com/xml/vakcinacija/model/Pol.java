package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Pol {
	@XmlEnumValue("Muski")
	MUSKI("Muski"),
	
    @XmlEnumValue("Zenski")
    ZENSKI("Zenski");
    
    private final String pol;

	private Pol(String pol) { this.pol = pol; }
	
	@Override
	public String toString() {
		return this.pol;
	}
	
	public static Pol fromString(String text) {
        for (Pol p : Pol.values()) {
            if (p.toString().equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
	}
}
