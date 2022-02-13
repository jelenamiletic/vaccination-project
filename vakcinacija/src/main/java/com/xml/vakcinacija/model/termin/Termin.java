package com.xml.vakcinacija.model.termin;

import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "jmbg",
        "datum",
        "izvrseno",
        "brojDoze",
        "vakcina",
        "popunjenaSaglasnost"
})
@XmlRootElement(name = "termin")
public class Termin {
    public static final int DUZINA_VAKCINACIJE = 15;
    @XmlElement(required = true, name = "datum")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datum;
    @XmlElement(required = true, name = "jmbg")
    private String jmbg;
    @XmlElement(required = true, name = "izvrseno")
    private boolean izvrseno;
    @XmlElement(required = true, name = "popunjenaSaglasnost")
    private boolean popunjenaSaglasnost;
    @XmlElement(required = true, name = "broj_doze")
    private int brojDoze;
    @XmlElement(required = true, name = "vakcina")
    private String vakcina;
    
    public Termin() {
    	
    }

    public Termin(XMLGregorianCalendar datum, String jmbg, int brojDoze, String vakcina) {
		super();
		this.datum = datum;
		this.jmbg = jmbg;
		this.izvrseno = false;
		this.popunjenaSaglasnost = false;
		this.brojDoze = brojDoze;
		this.vakcina = vakcina;
	}

	public XMLGregorianCalendar getDatum() {
		return datum;
	}

	public void setDatum(XMLGregorianCalendar datum) {
		this.datum = datum;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public boolean isIzvrseno() {
		return izvrseno;
	}

	public void setIzvrseno(boolean izvrseno) {
		this.izvrseno = izvrseno;
	}

	public int getBrojDoze() {
		return brojDoze;
	}

	public void setBrojDoze(int brojDoze) {
		this.brojDoze = brojDoze;
	}

	public static int getDuzinaVakcinacije() {
		return DUZINA_VAKCINACIJE;
	}

	public boolean isPopunjenaSaglasnost() {
		return popunjenaSaglasnost;
	}

	public void setPopunjenaSaglasnost(boolean popunjenaSaglasnost) {
		this.popunjenaSaglasnost = popunjenaSaglasnost;
	}

	public String getVakcina() {
		return vakcina;
	}

	public void setVakcina(String vakcina) {
		this.vakcina = vakcina;
	}
}