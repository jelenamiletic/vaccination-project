package com.xml.sluzbenik.service;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;

public interface PretragaService {

	String osnovnaPretraga(String pretragaDokumenata) throws Exception;
	
	String naprednaPretraga(String pretragaDokumenata) throws Exception;
	
	InputStreamResource nabaviMetaPodatkePotvrdaRDFPoJmbg(String jmbg, int brojDoze) throws IOException, Exception;

	InputStreamResource nabaviMetaPodatkePotvrdaJSONPoJmbg(String jmbg, int brojDoze) throws Exception;

	InputStreamResource nabaviMetaPodatkeSaglasnostJSONPoId(String jmbg, int brojDoze) throws Exception;

	InputStreamResource nabaviMetaPodatkeSaglasnostRDFPoId(String jmbg, int brojDoze) throws Exception;

	InputStreamResource nabaviMetaPodatkeSertifikatRDFPoJmbg(String jmbg) throws Exception;

	InputStreamResource nabaviMetaPodatkeSertifikatJSONPoJmbg(String jmbg) throws Exception;
}
