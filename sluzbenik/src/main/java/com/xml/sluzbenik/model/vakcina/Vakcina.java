package com.xml.sluzbenik.model.vakcina;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.sluzbenik.model.Proizvodjac;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Naziv",
        "Kolicina"
})
@XmlRootElement(name = "Vakcina")
public class Vakcina {

	@XmlElement(required = true)
	protected Proizvodjac Naziv;
	
	@XmlElement(required = true)
	protected int Kolicina;

	public Proizvodjac getNaziv() {
		return Naziv;
	}

	public void setNaziv(Proizvodjac naziv) {
		Naziv = naziv;
	}

	public int getKolicina() {
		return Kolicina;
	}

	public void setKolicina(int kolicina) {
		Kolicina = kolicina;
	}
}
