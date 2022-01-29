package com.xml.vakcinacija.model.potvrda;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Potvrda createInteresovanje() {
		return new Potvrda();
	}
	
	public Potvrda.LicneInformacije createLicneInformacijePotvrda() {
		return new Potvrda.LicneInformacije();
	}
	
	public Potvrda.InformacijeOVakcinama createInformacijeOVakcinama() {
		return new Potvrda.InformacijeOVakcinama();
	}
	
	public Potvrda.Vakcine createVakcine() {
		return new Potvrda.Vakcine();
	}
	
	public ListaPotvrda createListaPotvrda() {
        return new ListaPotvrda();
    }
}
