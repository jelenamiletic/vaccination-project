package com.xml.sluzbenik.service;

import org.xml.sax.SAXException;

import com.xml.sluzbenik.model.OdgovorNaZahtev;
import com.xml.sluzbenik.model.potvrda.Potvrda;
import com.xml.sluzbenik.model.zahtev.ListaZahteva;

public interface OdgovorNaZahtevService {

	ListaZahteva dobaviSveNeodobreneZahteve() throws SAXException;
	
	void promeniStatusZahteva(OdgovorNaZahtev odgovorNaZahtev) throws SAXException;
	
	Potvrda dobaviPoslednjuPotvrduPoJmbg(String jmbg) throws SAXException;
	
	void dodajNoviSertifikat(String sertifikatXml) throws SAXException;
}
