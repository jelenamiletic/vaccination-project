package com.xml.vakcinacija.service;

import com.xml.vakcinacija.model.Interesovanje;

public interface InteresovanjeService {

	void dodajNovoInteresovanje(String interesovanjeXML) throws Exception;
	
	Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception;
}
