package com.xml.sluzbenik.model.zdravstveni_radnik;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public ZdravstveniRadnik createZdravstveniRadnik() {
		return new ZdravstveniRadnik();
	}
}
