package com.xml.vakcinacija.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.xml.vakcinacija.model.OdgovorNaZahtev;
import com.xml.vakcinacija.model.zahtev.Zahtev;

public interface ZahtevService {
	
	void dodajNoviZahtev(String zahtevXML) throws Exception;
	
	List<Zahtev> pronadjiSve() throws Exception;
	
	Zahtev pronadjiZahtevPoJmbg(String jmbg) throws Exception;
	
	void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException;
	
	ByteArrayInputStream generisiPdf(String jmbg) throws Exception;

	ByteArrayInputStream generisiXHTML(String jmbg) throws Exception;
  
	List<Zahtev> dobaviSveNeodobreneZahteve() throws Exception;
	
	void promeniStatusZahteva(OdgovorNaZahtev odgovorNaZahtev) throws Exception;

}
