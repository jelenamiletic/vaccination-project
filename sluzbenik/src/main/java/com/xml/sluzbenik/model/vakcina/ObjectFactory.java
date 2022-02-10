package com.xml.sluzbenik.model.vakcina;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Vakcina createVakcina() {
		return new Vakcina();
	}
	
	public ListaVakcina createListaVakcina() {
		return new ListaVakcina();
	}
}
