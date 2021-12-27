package com.xml.vakcinacija.service;

import java.util.List;

import com.xml.vakcinacija.model.potvrda.Potvrda;

public interface PotvrdaService {

void dodajNoviPotvrda(String PotvrdaXML) throws Exception;
	
	List<Potvrda> pronadjiSve() throws Exception;
	
	Potvrda pronadjiPotvrdaPoJmbg(String jmbg) throws Exception;
}
