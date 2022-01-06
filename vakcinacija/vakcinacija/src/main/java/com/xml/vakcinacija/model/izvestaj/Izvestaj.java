package com.xml.vakcinacija.model.izvestaj;

import com.xml.vakcinacija.model.Proizvodjac;

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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "PeriodIzvestaja", 
        "BrojPodnetihDokumenata", 
        "ZahteviZaDigitalniSertifikat", 
        "UkupanBrojDatihDoza",
        "KolicnaDozaPoRednomBroju",
        "RaspodelaDozaPoProizvodjacu"
})
@XmlRootElement(name = "Izvestaj")
public class Izvestaj {
	
	@XmlElement(required = true)
	protected PeriodIzvestaja PeriodIzvestaja;
	
	@XmlElement(required = true)
	protected BrojPodnetihDokumenata BrojPodnetihDokumenata;
	
	@XmlElement(required = true)
	protected ZahteviZaDigitalniSertifikat ZahteviZaDigitalniSertifikat;
	
	@XmlElement(required = true)
	protected int UkupanBrojDatihDoza;
	
	@XmlElement(required = true)
	protected List<KolicnaDozaPoRednomBroju> KolicnaDozaPoRednomBroju;
	
	@XmlElement(required = true)
	protected List<RaspodelaDozaPoProizvodjacu> RaspodelaDozaPoProizvodjacu;
	
	@XmlAttribute(name = "DatumIzdavanja")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar DatumIzdavanja;
	
	@XmlAttribute(name = "about")
	@XmlSchemaType(name = "anyURI")
    protected String about;
	
    @XmlAttribute(name = "vocab")
    protected String vocab;
    
	public PeriodIzvestaja getPeriodIzvestaja() {
		return PeriodIzvestaja;
	}

	public void setPeriodIzvestaja(PeriodIzvestaja periodIzvestaja) {
		PeriodIzvestaja = periodIzvestaja;
	}

	public BrojPodnetihDokumenata getBrojPodnetihDokumenata() {
		return BrojPodnetihDokumenata;
	}

	public void setBrojPodnetihDokumenata(BrojPodnetihDokumenata brojPodnetihDokumenata) {
		BrojPodnetihDokumenata = brojPodnetihDokumenata;
	}

	public ZahteviZaDigitalniSertifikat getZahteviZaDigitalniSertifikat() {
		return ZahteviZaDigitalniSertifikat;
	}

	public void setZahteviZaDigitalniSertifikat(ZahteviZaDigitalniSertifikat zahteviZaDigitalniSertifikat) {
		ZahteviZaDigitalniSertifikat = zahteviZaDigitalniSertifikat;
	}

	public int getUkupanBrojDatihDoza() {
		return UkupanBrojDatihDoza;
	}

	public void setUkupanBrojDatihDoza(int ukupanBrojDatihDoza) {
		UkupanBrojDatihDoza = ukupanBrojDatihDoza;
	}

	public List<KolicnaDozaPoRednomBroju> getKolicnaDozaPoRednomBroju() {
		return KolicnaDozaPoRednomBroju;
	}

	public void setKolicnaDozaPoRednomBroju(List<KolicnaDozaPoRednomBroju> kolicnaDozaPoRednomBroju) {
		KolicnaDozaPoRednomBroju = kolicnaDozaPoRednomBroju;
	}

	public List<RaspodelaDozaPoProizvodjacu> getRaspodelaDozaPoProizvodjacu() {
		return RaspodelaDozaPoProizvodjacu;
	}

	public void setRaspodelaDozaPoProizvodjacu(List<RaspodelaDozaPoProizvodjacu> raspodelaDozaPoProizvodjacu) {
		RaspodelaDozaPoProizvodjacu = raspodelaDozaPoProizvodjacu;
	}

	public XMLGregorianCalendar getDatumIzdavanja() {
		return DatumIzdavanja;
	}

	public void setDatumIzdavanja(XMLGregorianCalendar datumIzdavanja) {
		DatumIzdavanja = datumIzdavanja;
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
	        "OdDatum", 
	        "DoDatum"
	})
	public static class PeriodIzvestaja {
		
		@XmlElement(required = true)
		protected XMLGregorianCalendar OdDatum;
		
		@XmlElement(required = true)
		protected XMLGregorianCalendar DoDatum;

		public XMLGregorianCalendar getOdDatum() {
			return OdDatum;
		}

		public void setOdDatum(XMLGregorianCalendar odDatum) {
			OdDatum = odDatum;
		}

		public XMLGregorianCalendar getDoDatum() {
			return DoDatum;
		}

		public void setDoDatum(XMLGregorianCalendar doDatum) {
			DoDatum = doDatum;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "value", 
	})
	public static class BrojPodnetihDokumenata {
		
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
                return "pred:brojPodnetihDokumenata";
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
	        "BrojPrimljenih",
	        "BrojIzdatih"
	})
	public static class ZahteviZaDigitalniSertifikat {
		
		@XmlElement(required = true)
		protected BrojPrimljenih BrojPrimljenih;
		
		@XmlElement(required = true)
		protected BrojIzdatih BrojIzdatih;
		
		public BrojPrimljenih getBrojPrimljenih() {
			return BrojPrimljenih;
		}

		public void setBrojPrimljenih(BrojPrimljenih brojPrimljenih) {
			BrojPrimljenih = brojPrimljenih;
		}

		public BrojIzdatih getBrojIzdatih() {
			return BrojIzdatih;
		}

		public void setBrojIzdatih(BrojIzdatih brojIzdatih) {
			BrojIzdatih = brojIzdatih;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		        "value", 
		})
		public static class BrojPrimljenih {
			
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
	                return "pred:brojPrimljenih";
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
		        "value", 
		})
		public static class BrojIzdatih {
			
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
	                return "pred:brojIzdatih";
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
	        "RedniBroj",
	        "BrojDatihDoza"
	})
	public static class KolicnaDozaPoRednomBroju {
		
		@XmlElement(required = true)
		protected int RedniBroj;
		
		@XmlElement(required = true)
		protected int BrojDatihDoza;

		public int getRedniBroj() {
			return RedniBroj;
		}

		public void setRedniBroj(int redniBroj) {
			RedniBroj = redniBroj;
		}

		public int getBrojDatihDoza() {
			return BrojDatihDoza;
		}

		public void setBrojDatihDoza(int brojDatihDoza) {
			BrojDatihDoza = brojDatihDoza;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "Proizvodjac",
	        "BrojDatihDoza"
	})
	public static class RaspodelaDozaPoProizvodjacu {
		
		@XmlElement(required = true)
		protected Proizvodjac Proizvodjac;
		
		@XmlElement(required = true)
		protected int BrojDatihDoza;

		public Proizvodjac getProizvodjac() {
			return Proizvodjac;
		}

		public void setProizvodjac(Proizvodjac proizvodjac) {
			Proizvodjac = proizvodjac;
		}

		public int getBrojDatihDoza() {
			return BrojDatihDoza;
		}

		public void setBrojDatihDoza(int brojDatihDoza) {
			BrojDatihDoza = brojDatihDoza;
		}
	}
}
