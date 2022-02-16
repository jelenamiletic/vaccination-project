package com.xml.sluzbenik.service;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;

public interface PretragaService {

	String osnovnaPretraga(String pretragaDokumenata) throws Exception;
	
	String naprednaPretraga(String pretragaDokumenata) throws Exception;
	
	InputStreamResource nabaviMetaPodatkePotvrdaRDFPoJmbg(String jmbg, int brojDoze) throws IOException, Exception;

	InputStreamResource nabaviMetaPodatkePotvrdaJSONPoJmbg(String jmbg, int brojDoze) throws Exception;
}
