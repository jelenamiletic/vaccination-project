package com.xml.sluzbenik.model.zahtev;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Zahtev"
})
@XmlRootElement(name = "ListaZahteva")
public class ListaZahteva {

	@XmlElement(name = "Zahtev")
	protected List<Zahtev> Zahtev;
	
	public List<Zahtev> getZahtev() {
        if (Zahtev == null) {
        	Zahtev = new ArrayList<Zahtev>();
        }
        return this.Zahtev;
    }

    public void setZahtev(List<Zahtev> Zahtev) {
        this.Zahtev = Zahtev;
    }
}
