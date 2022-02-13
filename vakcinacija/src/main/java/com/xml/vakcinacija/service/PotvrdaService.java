package com.xml.vakcinacija.service;

import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.potvrda.Potvrda;

public interface PotvrdaService {

void dodajNoviPotvrda(String PotvrdaXML) throws Exception;
	
	List<Potvrda> pronadjiSve() throws Exception;
	
	void nabaviMetaPodatkeXmlPoJmbg(String jmbg, int brojDoze) throws IOException;

	Potvrda pronadjiPotvrdaPoJmbg(String jmbg, int brojDoze) throws Exception;
	
	Potvrda dobaviPoslednjuPotvrduPoJmbg(String jmbg) throws Exception;
}
