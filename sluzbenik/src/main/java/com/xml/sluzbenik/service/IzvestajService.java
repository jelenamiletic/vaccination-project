package com.xml.sluzbenik.service;

import java.io.IOException;
import java.util.List;

import com.xml.sluzbenik.model.izvestaj.Izvestaj;

public interface IzvestajService {

	void dodajNoviIzvestaj(String izvestajXML) throws Exception;
	
	List<Izvestaj> pronadjiSve() throws Exception;
	
	Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception;

	void nabaviMetaPodatkeXmlPoDatumima(String odDatum, String doDatum) throws IOException;
}
