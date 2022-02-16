package com.xml.vakcinacija.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;

public interface InteresovanjeService {

	void dodajNovoInteresovanje(String interesovanjeXML) throws Exception;
	
	List<Interesovanje> pronadjiSve() throws Exception;
	
	Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception;

	ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg) throws IOException;
	
	ByteArrayInputStream nabaviMetaPodatkeRDFPoJmbg(String jmbg) throws IOException, Exception;
	
	ByteArrayInputStream generisiXHTML(String jmbg) throws Exception;
	
	ByteArrayInputStream generisiPdf(String jmbg) throws Exception;
	
	String pronadjiSveOsnovnaPretraga(String pretraga) throws Exception;
}
