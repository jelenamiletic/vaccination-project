package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.vakcinacija.utils.XMLNamespaceKonstante;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "LicneInformacije", 
        "OpstinaPrimanja", 
        "Vakcina", 
        "DavalacKrvi"
})
@XmlRootElement(name = "Interesovanje", namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
public class Interesovanje {
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
    protected Interesovanje.LicneInformacije LicneInformacije;
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
	protected String OpstinaPrimanja;
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
	protected Proizvodjac Vakcina;
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
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
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected LicneInformacije.PunoIme PunoIme;
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected String Drzavljanstvo;
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected String JMBG;
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected String AdresaElektronskePoste;
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected String BrojMobilnogTelefona;
		
		@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE)
		protected String BrojFiksnogTelefona;
		
		public LicneInformacije.PunoIme getPunoIme() {
			if (PunoIme == null) {
				PunoIme = new LicneInformacije.PunoIme();
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

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		    "Ime",
		    "Prezime"
		})
		public static class PunoIme {
			
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
	}

}
