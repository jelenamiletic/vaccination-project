package com.xml.vakcinacija.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.saglasnost.Saglasnost;

public interface SaglasnostService {

	void dodajNovuSaglasnost(String XML) throws Exception;
	
	void izmeniSaglasnost(String XML) throws Exception;
	
	List<Saglasnost> pronadjiSve() throws Exception;
	
	List<Saglasnost> pronadjiSaglasnostPoJmbgIliBrPasosa(String id) throws Exception;
	
	Saglasnost pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa(String id) throws Exception;

	void nabaviMetaPodatkeXmlPoId(String id) throws IOException;
	
	ByteArrayInputStream generisiPdf(String jmbg) throws Exception;

	ByteArrayInputStream generisiXhtml(String jmbg) throws Exception;
}
