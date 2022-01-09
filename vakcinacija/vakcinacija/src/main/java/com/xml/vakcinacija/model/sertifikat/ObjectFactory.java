package com.xml.vakcinacija.model.sertifikat;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory(){

    }

    public Sertifikat createSertifikat() {return new Sertifikat();}

    public ListaSertifikata createListaSertifikata() {return new ListaSertifikata();}

}
