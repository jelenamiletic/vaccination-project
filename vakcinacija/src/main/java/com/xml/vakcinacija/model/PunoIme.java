package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.vakcinacija.utils.XMLNamespaceKonstante;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Ime",
    "Prezime"
})
public class PunoIme {

	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES)
	protected String Ime;
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES)
	protected String Prezime;

	public String getIme() {
		return Ime;
	}

	public void setIme(String Ime) {
		this.Ime = Ime;
	}

	public String getPrezime() {
		return Prezime;
	}

	public void setPrezime(String Prezime) {
		this.Prezime = Prezime;
	}
}
