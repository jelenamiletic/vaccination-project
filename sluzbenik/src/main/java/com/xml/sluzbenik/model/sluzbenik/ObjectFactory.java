package com.xml.sluzbenik.model.sluzbenik;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Sluzbenik createSluzbenik() {
		return new Sluzbenik();
	}
}
