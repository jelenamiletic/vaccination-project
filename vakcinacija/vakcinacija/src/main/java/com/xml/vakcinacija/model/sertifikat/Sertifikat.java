package com.xml.vakcinacija.model.sertifikat;

import com.xml.vakcinacija.model.Pol;
import com.xml.vakcinacija.model.Proizvodjac;
import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.RezultatTesta;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "BrojSertifikata",
        "DatumVremeIzdavanja",
        "QR",
        "LicneInformacije",
        "Vakcinacija",
        "Test",
})
@XmlRootElement(name = "Sertifikat")
public class Sertifikat {

    @XmlElement(required = true)
    protected String BrojSertifikata;

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar DatumVremeIzdavanja;

    @XmlElement(required = true)
    protected String QR;

    @XmlElement(required = true)
    protected Sertifikat.LicneInformacije LicneInformacije;

    @XmlElement(required = true)
    protected List<Sertifikat.Vakcinacija> Vakcinacija;

    @XmlElement()
    protected List<Sertifikat.Test> Test;

    @XmlAttribute(name = "about")
    @XmlSchemaType(name = "anyURI")
    protected String about;

    @XmlAttribute(name = "vocab")
    protected String vocab;

    public String getBrojSertifikata() {
        return BrojSertifikata;
    }

    public void setBrojSertifikata(String brojSertifikata) {
        BrojSertifikata = brojSertifikata;
    }

    public XMLGregorianCalendar getDatumVremeIzdavanja() {
        return DatumVremeIzdavanja;
    }

    public void setDatumVremeIzdavanja(XMLGregorianCalendar datumVremeIzdavanja) {
        DatumVremeIzdavanja = datumVremeIzdavanja;
    }

    public String getQR() {
        return QR;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    public Sertifikat.LicneInformacije getLicneInformacije() {
        if (LicneInformacije == null) {
            LicneInformacije = new Sertifikat.LicneInformacije();
        }
        return LicneInformacije;
    }

    public void setLicneInformacije(Sertifikat.LicneInformacije licneInformacije) {
        LicneInformacije = licneInformacije;
    }

    public List<Sertifikat.Vakcinacija> getVakcinacija() {
        if (Vakcinacija == null) {
            Vakcinacija = new ArrayList<>();
        }
        return Vakcinacija;
    }

    public void setVakcinacija(List<Sertifikat.Vakcinacija> vakcinacija) {
        Vakcinacija = vakcinacija;
    }

    public List<Sertifikat.Test> getTest() {
        if (Test == null) {
            Test = new ArrayList<>();
        }
        return Test;
    }

    public void setTest(List<Sertifikat.Test> test) {
        Test = test;
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
    public static class LicneInformacije {

        @XmlElement(required = true)
        protected com.xml.vakcinacija.model.PunoIme PunoIme;

        @XmlElement(required = true)
        protected XMLGregorianCalendar DatumRodjenja;

        @XmlElement(required = true)
        protected com.xml.vakcinacija.model.Pol Pol;

        @XmlElement(required = true)
        protected JMBG JMBG;

        @XmlElement(required = true)
        protected BrojPasosa BrojPasosa;

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

        public BrojPasosa getBrojPasosa() {
            return BrojPasosa;
        }

        public void setBrojPasosa(BrojPasosa brojPasosa) {
            BrojPasosa = brojPasosa;
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
                    return "pred:brojPasosa";
                } else {
                    return property;
                }
            }

            public void setProperty(String value) {
                this.property = value;
            }

            public String getDatatype() {
                return datatype;
            }

            public void setDatatype(String value) {
                this.datatype = value;
            }

        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "BrojDoze",
            "TipVakcine",
            "DatumDavanja",
            "Proizvodjac",
            "Serija",
            "ZdravstvenaUstanova"
    })
    public static class Vakcinacija {

        @XmlElement(required = true)
        protected int BrojDoze;

        @XmlElement(required = true)
        protected Proizvodjac TipVakcine;

        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar DatumDavanja;

        @XmlElement(required = true)
        protected String Proizvodjac;

        @XmlElement(required = true)
        protected String Serija;

        @XmlElement(required = true)
        protected String ZdravstvenaUstanova;


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

        public com.xml.vakcinacija.model.Proizvodjac getTipVakcine() {
            return TipVakcine;
        }

        public void setTipVakcine(com.xml.vakcinacija.model.Proizvodjac tipVakcine) {
            TipVakcine = tipVakcine;
        }

        public String getProizvodjac() {
            return Proizvodjac;
        }

        public void setProizvodjac(String proizvodjac) {
            Proizvodjac = proizvodjac;
        }

        public String getZdravstvenaUstanova() {
            return ZdravstvenaUstanova;
        }

        public void setZdravstvenaUstanova(String zdravstvenaUstanova) {
            ZdravstvenaUstanova = zdravstvenaUstanova;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ImeTesta",
            "VrstaUzorka",
            "ProizvodjacTesta",
            "DatumVremeUzorkovanja",
            "DatumVremeIzdavanjaRezultata",
            "Rezultat",
            "Laboratorija"
    })
    public static class Test{

        @XmlElement(required = true)
        protected String ImeTesta;

        @XmlElement(required = true)
        protected String VrstaUzorka;

        @XmlElement(required = true)
        protected String ProizvodjacTesta;

        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar DatumVremeUzorkovanja;

        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar DatumVremeIzdavanjaRezultata;

        @XmlElement(required = true)
        protected RezultatTesta Rezultat;

        @XmlElement(required = true)
        protected String Laboratorija;

        public String getImeTesta() {
            return ImeTesta;
        }

        public void setImeTesta(String imeTesta) {
            ImeTesta = imeTesta;
        }

        public String getVrstaUzorka() {
            return VrstaUzorka;
        }

        public void setVrstaUzorka(String vrstaUzorka) {
            VrstaUzorka = vrstaUzorka;
        }

        public String getProizvodjacTesta() {
            return ProizvodjacTesta;
        }

        public void setProizvodjacTesta(String proizvodjacTesta) {
            ProizvodjacTesta = proizvodjacTesta;
        }

        public XMLGregorianCalendar getDatumVremeUzorkovanja() {
            return DatumVremeUzorkovanja;
        }

        public void setDatumVremeUzorkovanja(XMLGregorianCalendar datumVremeUzorkovanja) {
            DatumVremeUzorkovanja = datumVremeUzorkovanja;
        }

        public XMLGregorianCalendar getDatumVremeIzdavanjaRezultata() {
            return DatumVremeIzdavanjaRezultata;
        }

        public void setDatumVremeIzdavanjaRezultata(XMLGregorianCalendar datumVremeIzdavanjaRezultata) {
            DatumVremeIzdavanjaRezultata = datumVremeIzdavanjaRezultata;
        }

        public RezultatTesta getRezultat() {
            return Rezultat;
        }

        public void setRezultat(RezultatTesta rezultat) {
            Rezultat = rezultat;
        }

        public String getLaboratorija() {
            return Laboratorija;
        }

        public void setLaboratorija(String laboratorija) {
            Laboratorija = laboratorija;
        }
    }
}
