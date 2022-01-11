package com.xml.vakcinacija.service;

import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.model.saglasnost.Saglasnost;

public interface SaglasnostService {

	void dodajNovuSaglasnost(String XML) throws Exception;
	
	List<Saglasnost> pronadjiSve() throws Exception;
	
	Saglasnost pronadjiSaglasnostPoJmbg(String jmbg) throws Exception;

	void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException;
}
