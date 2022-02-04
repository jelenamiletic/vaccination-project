package com.xml.vakcinacija.model.zdravstveni_radnik;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.vakcinacija.model.Korisnik;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ZdravstvenaUstanova",
        "Punkt"
})
@XmlRootElement(name = "ZdravstveniRadnik")
public class ZdravstveniRadnik extends Korisnik {
	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
	protected String ZdravstvenaUstanova;
	
	@XmlElement(required = true)
	protected int Punkt;

	public String getZdravstvenaUstanova() {
		return ZdravstvenaUstanova;
	}

	public void setZdravstvenaUstanova(String zdravstvenaUstanova) {
		ZdravstvenaUstanova = zdravstvenaUstanova;
	}

	public int getPunkt() {
		return Punkt;
	}

	public void setPunkt(int punkt) {
		Punkt = punkt;
	}
}
