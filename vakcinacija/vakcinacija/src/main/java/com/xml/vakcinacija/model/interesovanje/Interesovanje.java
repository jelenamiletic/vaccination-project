package com.xml.vakcinacija.model.interesovanje;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.vakcinacija.model.Proizvodjac;
import com.xml.vakcinacija.model.PunoIme;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "LicneInformacije", 
        "OpstinaPrimanja", 
        "Vakcina", 
        "DavalacKrvi"
})
@XmlRootElement(name = "Interesovanje")
public class Interesovanje {
	
	@XmlElement(required = true)
    protected Interesovanje.LicneInformacije LicneInformacije;
	
	@XmlElement(required = true)
	protected String OpstinaPrimanja;
	
	@XmlElement(required = true)
	protected Proizvodjac Vakcina;
	
	@XmlElement(required = true)
	protected boolean DavalacKrvi;
	
	public Interesovanje.LicneInformacije getLicneInformacije() {
		if (LicneInformacije == null) {
			LicneInformacije = new Interesovanje.LicneInformacije();
        }
        return this.LicneInformacije;
	}
	
	public String getOpstinaPrimanja() {
		return OpstinaPrimanja;
	}

	public void setOpstinaPrimanja(String OpstinaPrimanja) {
		this.OpstinaPrimanja = OpstinaPrimanja;
	}

	public Proizvodjac getVakcina() {
		return Vakcina;
	}

	public void setVakcina(Proizvodjac Vakcina) {
		this.Vakcina = Vakcina;
	}

	public boolean isDavalacKrvi() {
		return DavalacKrvi;
	}

	public void setDavalacKrvi(boolean DavalacKrvi) {
		this.DavalacKrvi = DavalacKrvi;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "Drzavljanstvo",
	    "JMBG",
	    "PunoIme",
	    "AdresaElektronskePoste",
	    "BrojMobilnogTelefona",
	    "BrojFiksnogTelefona"
	})
	public static class LicneInformacije {
		
		@XmlElement(required = true)
		protected PunoIme PunoIme;
		
		@XmlElement(required = true)
		protected String Drzavljanstvo;
		
		@XmlElement(required = true)
		protected String JMBG;
		
		@XmlElement(required = true)
		protected String AdresaElektronskePoste;
		
		@XmlElement(required = true)
		protected String BrojMobilnogTelefona;
		
		@XmlElement(required = true)
		protected String BrojFiksnogTelefona;
		
		public PunoIme getPunoIme() {
			if (PunoIme == null) {
				PunoIme = new PunoIme();
	        }
	        return this.PunoIme;
		}
		
		public String getDrzavljanstvo() {
			return Drzavljanstvo;
		}

		public void setDrzavljanstvo(String Drzavljanstvo) {
			this.Drzavljanstvo = Drzavljanstvo;
		}

		public String getJMBG() {
			return JMBG;
		}

		public void setJMBG(String JMBG) {
			this.JMBG = JMBG;
		}

		public String getAdresaElektronskePoste() {
			return AdresaElektronskePoste;
		}

		public void setAdresaElektronskePoste(String AdresaElektronskePoste) {
			this.AdresaElektronskePoste = AdresaElektronskePoste;
		}

		public String getBrojMobilnogTelefona() {
			return BrojMobilnogTelefona;
		}

		public void setBrojMobilnogTelefona(String BrojMobilnogTelefona) {
			this.BrojMobilnogTelefona = BrojMobilnogTelefona;
		}

		public String getBrojFiksnogTelefona() {
			return BrojFiksnogTelefona;
		}

		public void setBrojFiksnogTelefona(String BrojFiksnogTelefona) {
			this.BrojFiksnogTelefona = BrojFiksnogTelefona;
		}
	}

}
