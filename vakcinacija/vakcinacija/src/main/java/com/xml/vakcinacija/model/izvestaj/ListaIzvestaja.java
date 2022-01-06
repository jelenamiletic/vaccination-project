package com.xml.vakcinacija.model.izvestaj;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Izvestaj"
})
@XmlRootElement(name = "ListaIzvestaja")
public class ListaIzvestaja {

	@XmlElement(name = "Izvestaj")
	protected List<Izvestaj> Izvestaj;
	
	public List<Izvestaj> getIzvestaj() {
        if (Izvestaj == null) {
        	Izvestaj = new ArrayList<Izvestaj>();
        }
        return this.Izvestaj;
    }

    public void setIzvestaj(List<Izvestaj> Izvestaj) {
        this.Izvestaj = Izvestaj;
    }
}
