package com.xml.vakcinacija.service;

import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.izvestaj.Izvestaj;

public interface IzvestajService {

	void dodajNoviIzvestaj(String izvestajXML) throws Exception;
	
	List<Izvestaj> pronadjiSve() throws Exception;
	
	Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception;

	void nabaviMetaPodatkeXmlPoDatumima(String odDatum, String doDatum) throws IOException;
}
