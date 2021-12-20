package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Interesovanje createInteresovanje() {
		return new Interesovanje();
	}
	
	public Interesovanje.LicneInformacije createLicneInformacijeInteresovanje() {
		return new Interesovanje.LicneInformacije();
	}
	
	public Interesovanje.LicneInformacije.PunoIme createPunoImeInteresovanje() {
		return new Interesovanje.LicneInformacije.PunoIme();
	}
}
