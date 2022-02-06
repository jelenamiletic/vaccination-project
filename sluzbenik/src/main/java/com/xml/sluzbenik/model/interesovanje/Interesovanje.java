package com.xml.sluzbenik.model.interesovanje;

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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.xml.sluzbenik.model.Proizvodjac;
import com.xml.sluzbenik.model.PunoIme;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "LicneInformacije", 
        "OpstinaPrimanja", 
        "Vakcina", 
        "DavalacKrvi",
        "DatumPodnosenja"
})
@XmlRootElement(name = "Interesovanje")
public class Interesovanje {
	
	@XmlElement(required = true)
    protected Interesovanje.LicneInformacije LicneInformacije;
	
	@XmlElement(required = true)
	protected OpstinaPrimanja OpstinaPrimanja;
	
	@XmlElement(required = true)
	protected Vakcina Vakcina;
	
	@XmlElement(required = true)
	protected boolean DavalacKrvi;
	
	@XmlElement
	protected XMLGregorianCalendar DatumPodnosenja;
	
	@XmlAttribute(name = "about")
	@XmlSchemaType(name = "anyURI")
    protected String about;
	
    @XmlAttribute(name = "vocab")
    protected String vocab;
	
	public Interesovanje.LicneInformacije getLicneInformacije() {
		if (LicneInformacije == null) {
			LicneInformacije = new Interesovanje.LicneInformacije();
        }
        return this.LicneInformacije;
	}
	
	public OpstinaPrimanja getOpstinaPrimanja() {
		return OpstinaPrimanja;
	}

	public void setOpstinaPrimanja(OpstinaPrimanja OpstinaPrimanja) {
		this.OpstinaPrimanja = OpstinaPrimanja;
	}

	public Vakcina getVakcina() {
		return Vakcina;
	}

	public void setVakcina(Vakcina Vakcina) {
		this.Vakcina = Vakcina;
	}

	public boolean isDavalacKrvi() {
		return DavalacKrvi;
	}
	
	public XMLGregorianCalendar getDatumPodnosenja() {
		return DatumPodnosenja;
	}

	public void setDatumPodnosenja(XMLGregorianCalendar datumPodnosenja) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		DatumPodnosenja = date2;
	}

	public void setDavalacKrvi(boolean DavalacKrvi) {
		this.DavalacKrvi = DavalacKrvi;
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
	    "value"
	})
	public static class OpstinaPrimanja {
		
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
                return "pred:opstinaPrimanja";
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
	public static class Vakcina {
		
		@XmlValue
        protected Proizvodjac value;
        @XmlAttribute(name = "property")
        protected String property;
        @XmlAttribute(name = "datatype")
        protected String datatype;
        
		public Proizvodjac getValue() {
			return value;
		}
		
		public void setValue(Proizvodjac value) {
			this.value = value;
		}
		
		public String getProperty() {
			if (property == null) {
                return "pred:vakcina";
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
		protected Drzavljanstvo Drzavljanstvo;
		
		@XmlElement(required = true)
		protected JMBG JMBG;
		
		@XmlElement(required = true)
		protected AdresaElektronskePoste AdresaElektronskePoste;
		
		@XmlElement(required = true)
		protected BrojMobilnogTelefona BrojMobilnogTelefona;
		
		@XmlElement(required = true)
		protected BrojFiksnogTelefona BrojFiksnogTelefona;
		
		public PunoIme getPunoIme() {
			if (PunoIme == null) {
				PunoIme = new PunoIme();
	        }
	        return this.PunoIme;
		}
		
		public Drzavljanstvo getDrzavljanstvo() {
			return Drzavljanstvo;
		}

		public void setDrzavljanstvo(Drzavljanstvo Drzavljanstvo) {
			this.Drzavljanstvo = Drzavljanstvo;
		}

		public JMBG getJMBG() {
			return JMBG;
		}

		public void setJMBG(JMBG JMBG) {
			this.JMBG = JMBG;
		}

		public AdresaElektronskePoste getAdresaElektronskePoste() {
			return AdresaElektronskePoste;
		}

		public void setAdresaElektronskePoste(AdresaElektronskePoste AdresaElektronskePoste) {
			this.AdresaElektronskePoste = AdresaElektronskePoste;
		}

		public BrojMobilnogTelefona getBrojMobilnogTelefona() {
			return BrojMobilnogTelefona;
		}

		public void setBrojMobilnogTelefona(BrojMobilnogTelefona BrojMobilnogTelefona) {
			this.BrojMobilnogTelefona = BrojMobilnogTelefona;
		}

		public BrojFiksnogTelefona getBrojFiksnogTelefona() {
			return BrojFiksnogTelefona;
		}

		public void setBrojFiksnogTelefona(BrojFiksnogTelefona BrojFiksnogTelefona) {
			this.BrojFiksnogTelefona = BrojFiksnogTelefona;
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		    "value"
		})
		public static class Drzavljanstvo {
			
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
	                return "pred:drzavljanstvo";
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
		public static class AdresaElektronskePoste {
			
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
	                return "pred:email";
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
		public static class BrojMobilnogTelefona {
			
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
	                return "pred:brojMobilnogTelefona";
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
		public static class BrojFiksnogTelefona {
			
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
	                return "pred:brojFiksnogTelefona";
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
