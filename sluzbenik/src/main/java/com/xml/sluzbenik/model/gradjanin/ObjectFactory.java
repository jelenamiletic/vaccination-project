package com.xml.sluzbenik.model.gradjanin;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Gradjanin createGradjanin() {
		return new Gradjanin();
	}
}
