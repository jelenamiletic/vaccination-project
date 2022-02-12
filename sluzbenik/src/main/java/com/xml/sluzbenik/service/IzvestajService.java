package com.xml.sluzbenik.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.xml.sluzbenik.model.Period;
import com.xml.sluzbenik.model.izvestaj.Izvestaj;

public interface IzvestajService {

	Izvestaj dodajNoviIzvestaj(Period period) throws Exception;
	
	List<Izvestaj> pronadjiSve() throws Exception;
	
	Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception;

	void nabaviMetaPodatkeXmlPoDatumima(String odDatum, String doDatum) throws IOException;
	
	ByteArrayInputStream generisiPdf(String odDatum, String doDatum) throws Exception;

	ByteArrayInputStream generisiXHTML(String odDatum, String doDatum) throws Exception;

}
