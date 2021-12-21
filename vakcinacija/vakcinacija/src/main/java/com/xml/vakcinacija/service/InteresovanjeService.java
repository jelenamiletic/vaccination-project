package com.xml.vakcinacija.service;

import java.util.List;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;

public interface InteresovanjeService {

	void dodajNovoInteresovanje(String interesovanjeXML) throws Exception;
	
	List<Interesovanje> pronadjiSve() throws Exception;
	
	String pronadjiInteresovanjeXmlPoJmbg(String jmbg) throws Exception;
	
	Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception;
}
