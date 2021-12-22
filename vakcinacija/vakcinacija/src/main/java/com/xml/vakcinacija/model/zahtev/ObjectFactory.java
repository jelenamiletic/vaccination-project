package com.xml.vakcinacija.model.zahtev;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Zahtev createInteresovanje() {
		return new Zahtev();
	}
	
	public Zahtev.Podnosilac createPodnosilacZahteva() {
		return new Zahtev.Podnosilac();
	}
	
	public ListaZahteva createListaZahteva() {
        return new ListaZahteva();
    }
}
