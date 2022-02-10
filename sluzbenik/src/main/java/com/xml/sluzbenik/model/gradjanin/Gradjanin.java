package com.xml.sluzbenik.model.gradjanin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.sluzbenik.model.Korisnik;
import com.xml.sluzbenik.model.Pol;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Pol",
        "Drzavljanstvo"
})
@XmlRootElement(name = "Gradjanin")
public class Gradjanin extends Korisnik {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required = true)
	protected Pol Pol;
	
	@XmlElement(required = true)
	protected String Drzavljanstvo;
	
	public Pol getPol() {
		return Pol;
	}

	public void setPol(Pol pol) {
		Pol = pol;
	}

	public String getDrzavljanstvo() {
		return Drzavljanstvo;
	}

	public void setDrzavljanstvo(String drzavljanstvo) {
		Drzavljanstvo = drzavljanstvo;
	}
}
