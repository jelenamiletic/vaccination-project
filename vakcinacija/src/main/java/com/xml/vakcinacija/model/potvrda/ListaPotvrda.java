package com.xml.vakcinacija.model.potvrda;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Potvrda"
})
@XmlRootElement(name = "ListaPotvrda")
public class ListaPotvrda {

	@XmlElement(name = "Potvrda")
	protected List<Potvrda> Potvrda;
	
	public List<Potvrda> getPotvrda() {
        if (Potvrda == null) {
        	Potvrda = new ArrayList<Potvrda>();
        }
        return this.Potvrda;
    }

    public void setPotvrda(List<Potvrda> Potvrda) {
        this.Potvrda = Potvrda;
    }
}
