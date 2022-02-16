package com.xml.vakcinacija.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.potvrda.Potvrda;

public interface PotvrdaService {

	void dodajNoviPotvrda(String PotvrdaXML) throws Exception;
	
	List<Potvrda> pronadjiSve() throws Exception;
	
	ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg, int brojDoze) throws IOException;
	
	ByteArrayInputStream nabaviMetaPodatkeRDFPoJmbg(String jmbg, int brojDoze) throws IOException, Exception;

	Potvrda pronadjiPotvrdaPoJmbg(String jmbg, int brojDoze) throws Exception;
	
	Potvrda dobaviPoslednjuPotvrduPoJmbg(String jmbg) throws Exception;
	
	ByteArrayInputStream generisiPdf(String jmbg, int brojDoze) throws Exception;

	String pronadjiSveOsnovnaPretraga(String pretraga) throws Exception;

	String pronadjiSveNaprednaPretraga(String ime, String prezime, String jmbg, String pol) throws Exception;

	ByteArrayInputStream generisiXHTML(String jmbg, int brojDoze) throws Exception;
}
