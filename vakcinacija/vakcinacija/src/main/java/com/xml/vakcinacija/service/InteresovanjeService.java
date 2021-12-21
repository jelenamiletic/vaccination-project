package com.xml.vakcinacija.service;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;

public interface InteresovanjeService {

	void dodajNovoInteresovanje(String interesovanjeXML) throws Exception;
	
	String pronadjiInteresovanjeXmlPoJmbg(String jmbg) throws Exception;
	
	Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception;
}
