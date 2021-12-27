package com.xml.vakcinacija.model.potvrda;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Potvrda.InformacijeOVakcinama> createInformacijeOVakcinamaPotvrda() {
		return new ArrayList<>();
	}
	
	public List<Potvrda.Vakcine> createVakcinePotvrda() {
		return new ArrayList<>();
	}
	
	public ListaPotvrda createListaPotvrda() {
        return new ListaPotvrda();
    }
}
