package com.xml.sluzbenik.model.sertifikat;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory(){

    }

    public Sertifikat createSertifikat() {return new Sertifikat();}

    public ListaSertifikata createListaSertifikata() {return new ListaSertifikata();}

    public Sertifikat.LicneInformacije createLicneInformacijeSertifikat() {
        return new Sertifikat.LicneInformacije();
    }

    public Sertifikat.Vakcinacija createVakcinacija() {
        return new Sertifikat.Vakcinacija();
    }

    public Sertifikat.Test createTest() {
        return new Sertifikat.Test();
    }

}
