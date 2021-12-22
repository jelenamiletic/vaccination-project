package com.xml.vakcinacija.model.zahtev;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.Pol;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Podnosilac", 
        "RazlogPodnosenja"
})
@XmlRootElement(name = "Zahtev")
public class Zahtev {
	@XmlElement(required = true)
	protected Zahtev.Podnosilac Podnosilac;

	@XmlElement(required = true)
	protected String RazlogPodnosenja;
	
	public Zahtev.Podnosilac getPodnosilac() {
		if(this.Podnosilac == null) {
			this.Podnosilac = new Zahtev.Podnosilac();
		}
			
		return this.Podnosilac;
	}

	public void setPodnosilac(Zahtev.Podnosilac podnosilac) {
		Podnosilac = podnosilac;
	}

	public String getRazlogPodnosenja() {
		return RazlogPodnosenja;
	}

	public void setRazlogPodnosenja(String razlogPodnosenja) {
		RazlogPodnosenja = razlogPodnosenja;
	}



	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	    "DatumRodjenja",
	    "JMBG",
	    "PunoIme",
	    "Pol",
	    "BrojPasosa"
	})
	public static class Podnosilac{
		@XmlElement(required = true)
		protected Date DatumRodjenja;
		
		@XmlElement(required = true)
		protected PunoIme PunoIme;
		
		@XmlElement(required = true)
		protected String JMBG;
		
		@XmlElement(required = true)
		protected String BrojPasosa;
		
		@XmlElement(required = true)
		protected Pol Pol;

		public Date getDatumRodjenja() {
			return DatumRodjenja;
		}

		public void setDatumRodjenja(Date datumRodjenja) {
			DatumRodjenja = datumRodjenja;
		}

		public PunoIme getPunoIme() {
			if (PunoIme == null) {
				PunoIme = new PunoIme();
	        }
			return PunoIme;
		}

		public void setPunoIme(PunoIme punoIme) {
			PunoIme = punoIme;
		}

		public String getJMBG() {
			return JMBG;
		}

		public void setJMBG(String jMBG) {
			JMBG = jMBG;
		}

		public String getBrojPasosa() {
			return BrojPasosa;
		}

		public void setBrojPasosa(String brojPasosa) {
			BrojPasosa = brojPasosa;
		}

		public Pol getPol() {
			return Pol;
		}

		public void setPol(Pol pol) {
			Pol = pol;
		}
		
	}
}
