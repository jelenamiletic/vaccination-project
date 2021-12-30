package com.xml.vakcinacija.service;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;

public interface InteresovanjeService {

	void dodajNovoInteresovanje(String interesovanjeXML) throws Exception;
	
	List<Interesovanje> pronadjiSve() throws Exception;
	
	Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception;
	
	void upisiMetapodatke(String xml) throws SAXException;
	
	void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException;
}
