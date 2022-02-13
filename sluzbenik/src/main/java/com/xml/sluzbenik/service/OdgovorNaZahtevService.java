package com.xml.sluzbenik.service;

import org.xml.sax.SAXException;

import com.xml.sluzbenik.model.OdgovorNaZahtev;
import com.xml.sluzbenik.model.zahtev.ListaZahteva;

public interface OdgovorNaZahtevService {

	ListaZahteva dobaviSveNeodobreneZahteve() throws SAXException;
	
	void promeniStatusZahteva(String jmbg, OdgovorNaZahtev odgovorNaZahtev) throws SAXException;
}
