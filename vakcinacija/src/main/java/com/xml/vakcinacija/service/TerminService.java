package com.xml.vakcinacija.service;

import java.util.List;

import com.xml.vakcinacija.model.termin.Termin;

public interface TerminService {
	
	List<Termin> getTerminePoJmbg(String jmbg) throws Exception;
	
	List<Termin> getSveTermine() throws Exception;
	
	Termin getAktuelniTerminPoJmbg(String jmbg) throws Exception;
	 
	Termin getTerminPoJmbgIDozi(String jmbg, int brojDoze) throws Exception;
	
	void postaviPopunjenaSaglasnost(String jmbg, int brojDoze) throws Exception;
	
	void postaviIzvrseno(String jmbg, int brojDoze) throws Exception;

	Termin dodajNoviTermin(String jmbg, int brojDoze, String vakcina, boolean gradjanin) throws Exception;
}
