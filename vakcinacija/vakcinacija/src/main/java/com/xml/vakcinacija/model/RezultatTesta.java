package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum RezultatTesta {
    @XmlEnumValue("Pozitivan")
    POZITIVAN("Pozitivan"),

    @XmlEnumValue("Negativan")
    NEGATIVAN("Negativan"),
    ;

    private final String rezultat;

    private RezultatTesta(String rezultat) {
        this.rezultat = rezultat;
    }

    @Override
    public String toString() {
        return this.rezultat;
    }

    public static RezultatTesta fromString(String text) {
        for (RezultatTesta r : RezultatTesta.values()) {
            if (r.toString().equalsIgnoreCase(text)) {
                return r;
            }
        }
        return null;
    }
}
