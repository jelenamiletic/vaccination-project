package com.xml.sluzbenik.service;

import java.util.List;

import com.xml.sluzbenik.model.vakcina.Vakcina;

public interface VakcinaService {

	List<Vakcina> dobaviSve() throws Exception;
	
	void azurirajKolicinu(Vakcina vakcina) throws Exception;

	void smanjiKolicinu(String nazivVakcine) throws Exception;
	
	boolean proveriISmanji(String nazivVakcine) throws Exception;
}
