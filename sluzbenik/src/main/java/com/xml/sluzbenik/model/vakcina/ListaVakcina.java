package com.xml.sluzbenik.model.vakcina;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Vakcina"
})
@XmlRootElement(name = "ListaVakcina")
public class ListaVakcina {

	@XmlElement(name = "Vakcina")
	protected List<Vakcina> Vakcina;
	
	public List<Vakcina> getVakcina() {
        if (Vakcina == null) {
        	Vakcina = new ArrayList<Vakcina>();
        }
        return this.Vakcina;
    }

    public void setVakcina(List<Vakcina> Vakcina) {
        this.Vakcina = Vakcina;
    }
}
