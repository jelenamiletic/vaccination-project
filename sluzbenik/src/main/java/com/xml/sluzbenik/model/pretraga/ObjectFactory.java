package com.xml.sluzbenik.model.pretraga;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public PretragaDokumenata createInteresovanje() {
		return new PretragaDokumenata();
	}
}
