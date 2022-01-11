package com.xml.vakcinacija.model.saglasnost;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Interesovanje"
})
@XmlRootElement(name = "ListaSaglasnosti")
public class ListaSaglasnosti {

	@XmlElement(name = "Interesovanje")
	protected List<Saglasnost> Saglasnost;
	
	public List<Saglasnost> getSaglasnost() {
        if (Saglasnost == null) {
        	Saglasnost = new ArrayList<Saglasnost>();
        }
        return this.Saglasnost;
    }

    public void setSaglasnost(List<Saglasnost> Saglasnost) {
        this.Saglasnost = Saglasnost;
    }
}
