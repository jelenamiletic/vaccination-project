package com.xml.vakcinacija.model.potvrda;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.xml.vakcinacija.model.Proizvodjac;
import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.Pol;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"Sifra",
        "LicneInformacije", 
        "InformacijeOVakcinama",
        "ZdravstvenaUstanova",
        "VakcinaPrveDveDoze", 
        "Vakcine",
        "DatumIzdavanja",
        "QR"
})
@XmlRootElement(name = "Potvrda")
public class Potvrda {

	@XmlElement(required = true)
	protected String Sifra;
	
	@XmlElement(required = true)
	protected String ZdravstvenaUstanova;
	
	@XmlElement(required = true)
	protected Proizvodjac VakcinaPrveDveDoze;
	
	@XmlElement(required = true)
	protected XMLGregorianCalendar DatumIzdavanja;
	
	@XmlElement(required = true)
	protected String QR;
	
	@XmlElement(required = true)
	protected Potvrda.LicneInformacije LicneInformacije;
	
	@XmlElement(required = true)
	protected List<Potvrda.InformacijeOVakcinama> InformacijeOVakcinama;
	
	@XmlElement()
	protected List<Potvrda.Vakcine> Vakcine;
	
	public Potvrda.LicneInformacije getLicneInformacije() {
		if (LicneInformacije == null) {
			LicneInformacije = new Potvrda.LicneInformacije();
        }
		return LicneInformacije;
	}

	public void setLicneInformacije(Potvrda.LicneInformacije licneInformacije) {
		LicneInformacije = licneInformacije;
	}

	public List<Potvrda.InformacijeOVakcinama> getInformacijeOVakcinama() {
		if (InformacijeOVakcinama == null) {
			InformacijeOVakcinama = new ArrayList<>();
        }
		return InformacijeOVakcinama;
	}

	public void setInformacijeOVakcinama(List<Potvrda.InformacijeOVakcinama> informacijeOVakcinama) {
		InformacijeOVakcinama = informacijeOVakcinama;
	}

	public List<Potvrda.Vakcine> getVakcine() {
		if (Vakcine == null) {
			Vakcine = new ArrayList<>();
        }
		return Vakcine;
	}

	public void setVakcine(List<Potvrda.Vakcine> vakcine) {
		Vakcine = vakcine;
	}

	public String getSifra() {
		return Sifra;
	}

	public void setSifra(String sifra) {
		Sifra = sifra;
	}

	public String getZdravstvenaUstanova() {
		return ZdravstvenaUstanova;
	}

	public void setZdravstvenaUstanova(String zdravstvenaUstanova) {
		ZdravstvenaUstanova = zdravstvenaUstanova;
	}

	public Proizvodjac getVakcinaPrveDveDoze() {
		return VakcinaPrveDveDoze;
	}

	public void setVakcinaPrveDveDoze(Proizvodjac vakcinaPrveDveDoze) {
		VakcinaPrveDveDoze = vakcinaPrveDveDoze;
	}

	public XMLGregorianCalendar getDatumIzdavanja() {
		return DatumIzdavanja;
	}

	public void setDatumIzdavanja(XMLGregorianCalendar datumIzdavanja) {
		DatumIzdavanja = datumIzdavanja;
	}

	public String getQR() {
		return QR;
	}

	public void setQR(String qR) {
		QR = qR;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "PunoIme",
	    "DatumRodjenja",
	    "Pol",
	    "JMBG"
	})
	public static class LicneInformacije {
		
		@XmlElement(required = true)
		protected PunoIme PunoIme;
		
		@XmlElement(required = true)
		protected XMLGregorianCalendar DatumRodjenja;
		
		@XmlElement(required = true)
		protected Pol Pol;
		
		@XmlElement(required = true)
		protected String JMBG;

		public PunoIme getPunoIme() {
			if (PunoIme == null) {
				PunoIme = new PunoIme();
	        }
			return PunoIme;
		}

		public void setPunoIme(PunoIme punoIme) {
			PunoIme = punoIme;
		}

		public XMLGregorianCalendar getDatumRodjenja() {
			return DatumRodjenja;
		}

		public void setDatumRodjenja(XMLGregorianCalendar datumRodjenja) {
			DatumRodjenja = datumRodjenja;
		}

		public Pol getPol() {
			return Pol;
		}

		public void setPol(Pol pol) {
			Pol = pol;
		}

		public String getJMBG() {
			return JMBG;
		}

		public void setJMBG(String jMBG) {
			JMBG = jMBG;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "BrojDoze",
	    "DatumDavanja",
	    "Serija"
	})
	public static class InformacijeOVakcinama {
		
		@XmlElement(required = true)
		protected int BrojDoze;
		
		@XmlElement(required = true)
		protected XMLGregorianCalendar DatumDavanja;
		
		@XmlElement(required = true)
		protected String Serija;

		public int getBrojDoze() {
			return BrojDoze;
		}

		public void setBrojDoze(int brojDoze) {
			BrojDoze = brojDoze;
		}

		public XMLGregorianCalendar getDatumDavanja() {
			return DatumDavanja;
		}

		public void setDatumDavanja(XMLGregorianCalendar datumDavanja) {
			DatumDavanja = datumDavanja;
		}

		public String getSerija() {
			return Serija;
		}

		public void setSerija(String serija) {
			Serija = serija;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "BrojDoze",
	    "Vakcina"
	})
	public static class Vakcine {
		
		@XmlElement(required = true)
		protected Integer BrojDoze;
		
		@XmlElement(required = true)
		protected Proizvodjac Vakcina;

		public Integer getBrojDoze() {
			return BrojDoze;
		}

		public void setBrojDoze(Integer brojDoze) {
			BrojDoze = brojDoze;
		}

		public Proizvodjac getVakcina() {
			return Vakcina;
		}

		public void setVakcina(Proizvodjac vakcina) {
			Vakcina = vakcina;
		}
	}
}
