package com.xml.sluzbenik.model.pretraga;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.sluzbenik.model.Pol;
import com.xml.sluzbenik.model.TipDokumenta;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "TipDokumenta",
        "Ime",
        "Prezime",
        "JMBG",
        "Pol"
})
@XmlRootElement(name = "PretragaDokumenata")
public class PretragaDokumenata {
	
	@XmlElement
	protected String Ime;
	
	@XmlElement
	protected String Prezime;
	
	@XmlElement
	protected String JMBG;
	
	@XmlElement
	protected Pol pol;
	
	@XmlElement
	protected TipDokumenta TipDokumenta;

	public String getIme() {
		return Ime;
	}

	public void setIme(String ime) {
		Ime = ime;
	}

	public String getPrezime() {
		return Prezime;
	}

	public void setPrezime(String prezime) {
		Prezime = prezime;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public TipDokumenta getTipDokumenta() {
		return TipDokumenta;
	}

	public void setTipDokumenta(TipDokumenta tipDokumenta) {
		TipDokumenta = tipDokumenta;
	}
	
	
}
