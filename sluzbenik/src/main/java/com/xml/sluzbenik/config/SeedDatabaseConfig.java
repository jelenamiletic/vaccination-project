package com.xml.sluzbenik.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xml.sluzbenik.model.Proizvodjac;
import com.xml.sluzbenik.model.vakcina.Vakcina;
import com.xml.sluzbenik.repository.VakcinaRepository;

@Configuration
public class SeedDatabaseConfig {
	
	@Autowired
	private VakcinaRepository vakcinaRepository;
	
	@Bean
	public void seedDatabase() throws Exception {
		List<Vakcina> vakcineDb = vakcinaRepository.pronadjiSve();
		if (vakcineDb == null || vakcineDb.isEmpty()) {
			for (Proizvodjac p : Proizvodjac.values()) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(p);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			}
		} else if (vakcineDb.size() < Proizvodjac.values().length) {
			List<Proizvodjac> naziviVakcina = new ArrayList<Proizvodjac>();
			for (Vakcina v : vakcineDb) {
				naziviVakcina.add(v.getNaziv());
			}
			
			if (!naziviVakcina.contains(Proizvodjac.PFIZER)) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(Proizvodjac.PFIZER);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			}
			if (!naziviVakcina.contains(Proizvodjac.MODERNA)) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(Proizvodjac.MODERNA);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			} 
			if (!naziviVakcina.contains(Proizvodjac.ASTRA_ZENECA)) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(Proizvodjac.ASTRA_ZENECA);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			} 
			if (!naziviVakcina.contains(Proizvodjac.SINOPHARM)) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(Proizvodjac.SINOPHARM);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			}
			if (!naziviVakcina.contains(Proizvodjac.SPUTNIK)) {
				Vakcina vakcina = new Vakcina();
				vakcina.setNaziv(Proizvodjac.SPUTNIK);
				vakcina.setKolicina(1000);
				vakcinaRepository.saveVakcinaObjekat(vakcina);
			}
		}
	}
}
