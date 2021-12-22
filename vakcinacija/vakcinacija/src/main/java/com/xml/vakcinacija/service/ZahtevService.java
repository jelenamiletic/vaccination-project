package com.xml.vakcinacija.service;

import java.util.List;

import com.xml.vakcinacija.model.zahtev.Zahtev;

public interface ZahtevService {
	
	void dodajNoviZahtev(String zahtevXML) throws Exception;
	
	List<Zahtev> pronadjiSve() throws Exception;
	
	Zahtev pronadjiZahtevPoJmbg(String jmbg) throws Exception;
}
