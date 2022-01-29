package com.xml.sluzbenik.model.interesovanje;

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
@XmlRootElement(name = "ListaInteresovanja")
public class ListaInteresovanja {

	@XmlElement(name = "Interesovanje")
	protected List<Interesovanje> Interesovanje;
	
	public List<Interesovanje> getInteresovanje() {
        if (Interesovanje == null) {
        	Interesovanje = new ArrayList<Interesovanje>();
        }
        return this.Interesovanje;
    }

    public void setInteresovanje(List<Interesovanje> Interesovanje) {
        this.Interesovanje = Interesovanje;
    }
}
