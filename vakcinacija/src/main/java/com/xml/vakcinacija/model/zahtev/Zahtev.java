package com.xml.vakcinacija.model.zahtev;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.Pol;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Podnosilac", 
        "RazlogPodnosenja",
        "Odobren",
        "DatumPodnosenja"
})
@XmlRootElement(name = "Zahtev")
public class Zahtev {
	@XmlElement(required = true)
	protected Zahtev.Podnosilac Podnosilac;

	@XmlElement(required = true)
	protected String RazlogPodnosenja;
	
	@XmlElement(required = true)
	protected boolean Odobren;
	
	@XmlElement
	protected XMLGregorianCalendar DatumPodnosenja;
	
	@XmlAttribute(name = "about")
	@XmlSchemaType(name = "anyURI")
    protected String about;
	
    @XmlAttribute(name = "vocab")
    protected String vocab;
	
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
	
	public boolean isOdobren() {
		return Odobren;
	}

	public void setOdobren(boolean odobren) {
		Odobren = odobren;
	}

	public XMLGregorianCalendar getDatumPodnosenja() {
		return DatumPodnosenja;
	}

	public void setDatumPodnosenja() throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		DatumPodnosenja = date2;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getVocab() {
		return vocab;
	}

	public void setVocab(String vocab) {
		this.vocab = vocab;
	}



	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
		"PunoIme",
	    "DatumRodjenja",
	    "Pol",
	    "JMBG",
	    "BrojPasosa"
	})
	public static class Podnosilac{
		@XmlElement(required = true)
		@XmlSchemaType(name = "date")
		protected XMLGregorianCalendar DatumRodjenja;
		
		@XmlElement(required = true)
		protected PunoIme PunoIme;
		
		@XmlElement(required = true)
		protected JMBG JMBG;
		
		@XmlElement(required = true)
		protected BrojPasosa BrojPasosa;
		
		@XmlElement(required = true)
		protected Pol Pol;

		public XMLGregorianCalendar getDatumRodjenja() {
			return DatumRodjenja;
		}

		public void setDatumRodjenja(XMLGregorianCalendar datumRodjenja) {
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

		public JMBG getJMBG() {
			return JMBG;
		}

		public void setJMBG(JMBG jMBG) {
			JMBG = jMBG;
		}

		public BrojPasosa getBrojPasosa() {
			return BrojPasosa;
		}

		public void setBrojPasosa(BrojPasosa brojPasosa) {
			BrojPasosa = brojPasosa;
		}

		public Pol getPol() {
			return Pol;
		}

		public void setPol(Pol pol) {
			Pol = pol;
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		    "value"
		})
		public static class JMBG {
			
			@XmlValue
	        protected String value;
	        @XmlAttribute(name = "property")
	        protected String property;
	        @XmlAttribute(name = "datatype")
	        protected String datatype;
	        
			public String getValue() {
				return value;
			}
			
			public void setValue(String value) {
				this.value = value;
			}
			
			public String getProperty() {
				if (property == null) {
	                return "pred:jmbg";
	            } else {
	                return property;
	            }
			}
			
			public void setProperty(String property) {
				this.property = property;
			}
			
			public String getDatatype() {
				return datatype;
			}
			
			public void setDatatype(String datatype) {
				this.datatype = datatype;
			}
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		    "value"
		})
		public static class BrojPasosa {
			
			@XmlValue
	        protected String value;
	        @XmlAttribute(name = "property")
	        protected String property;
	        @XmlAttribute(name = "datatype")
	        protected String datatype;
	        
			public String getValue() {
				return value;
			}
			
			public void setValue(String value) {
				this.value = value;
			}
			
			public String getProperty() {
				if (property == null) {
	                return "pred:BrojPasosa";
	            } else {
	                return property;
	            }
			}
			
			public void setProperty(String property) {
				this.property = property;
			}
			
			public String getDatatype() {
				return datatype;
			}
			
			public void setDatatype(String datatype) {
				this.datatype = datatype;
			}
		}
	}
}
