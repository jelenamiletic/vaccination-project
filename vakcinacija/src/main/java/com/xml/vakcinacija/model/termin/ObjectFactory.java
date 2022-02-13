package com.xml.vakcinacija.model.termin;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory(){

    }

    public Termin createTermin() {return new Termin();}

    public ListaTermina createListaTermina() {return new ListaTermina();}

}
