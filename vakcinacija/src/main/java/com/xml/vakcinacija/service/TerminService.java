package com.xml.vakcinacija.service;

import java.util.List;

import com.xml.vakcinacija.model.termin.Termin;

public interface TerminService {
	
	Termin dodajNoviTermin(String jmbg, int brojDoze) throws Exception;
	
	List<Termin> getTerminePoJmbg(String jmbg) throws Exception;
	
	List<Termin> getSveTermine() throws Exception;
	
	Termin getAktuelniTerminPoJmbg(String jmbg) throws Exception;
	 
	Termin getTerminPoJmbgIDozi(String jmbg, int brojDoze) throws Exception;
}
