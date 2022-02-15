package com.xml.sluzbenik.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum TipDokumenta {
	@XmlEnumValue("Saglasnost")
	SAGLASNOST("Saglasnost"),
    
	@XmlEnumValue("Potvrda")
    POTVRDA("Potvrda"),
    
	@XmlEnumValue("Sertifikat")
    SERTIFIKAT("Sertifikat");
	
    private final String tipDokumenta;

	private TipDokumenta(String tipDokumenta) { this.tipDokumenta = tipDokumenta; }
	
	@Override
	public String toString() {
		return this.tipDokumenta;
	}
	
	public static TipDokumenta fromString(String text) {
        for (TipDokumenta p : TipDokumenta.values()) {
            if (p.toString().equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
	}
}
