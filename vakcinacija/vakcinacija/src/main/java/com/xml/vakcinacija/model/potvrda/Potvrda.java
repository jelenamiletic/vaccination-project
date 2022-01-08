package com.xml.vakcinacija.model.potvrda;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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
	protected VakcinaPrveDveDoze VakcinaPrveDveDoze;
	
	@XmlElement(required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar DatumIzdavanja;
	
	@XmlElement(required = true)
	protected String QR;
	
	@XmlElement(required = true)
	protected Potvrda.LicneInformacije LicneInformacije;
	
	@XmlElement(required = true)
	protected List<Potvrda.InformacijeOVakcinama> InformacijeOVakcinama;
	
	@XmlElement()
	protected List<Potvrda.Vakcine> Vakcine;
	
	@XmlAttribute(name = "about")
	@XmlSchemaType(name = "anyURI")
    protected String about;
	
    @XmlAttribute(name = "vocab")
    protected String vocab;
	
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

	public VakcinaPrveDveDoze getVakcinaPrveDveDoze() {
		return VakcinaPrveDveDoze;
	}

	public void setVakcinaPrveDveDoze(VakcinaPrveDveDoze vakcinaPrveDveDoze) {
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
	public static class VakcinaPrveDveDoze {
		
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
                return "pred:VakcinaPrveDveDoze";
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
	    "PunoIme",
	    "DatumRodjenja",
	    "Pol",
	    "JMBG"
	})
	public static class LicneInformacije {
		
		@XmlElement(required = true)
		protected PunoIme PunoIme;
		
		@XmlElement(required = true)
		@XmlSchemaType(name = "date")
		protected XMLGregorianCalendar DatumRodjenja;
		
		@XmlElement(required = true)
		protected Pol Pol;
		
		@XmlElement(required = true)
		protected JMBG JMBG;

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

		public JMBG getJMBG() {
			return JMBG;
		}

		public void setJMBG(JMBG jMBG) {
			JMBG = jMBG;
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
		@XmlSchemaType(name = "date")
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
		protected Vakcina Vakcina;

		public Integer getBrojDoze() {
			return BrojDoze;
		}

		public void setBrojDoze(Integer brojDoze) {
			BrojDoze = brojDoze;
		}

		public Vakcina getVakcina() {
			return Vakcina;
		}

		public void setVakcina(Vakcina vakcina) {
			Vakcina = vakcina;
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
	                return "pred:Vakcina";
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
