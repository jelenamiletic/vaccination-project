package com.xml.sluzbenik.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.model.Proizvodjac;
import com.xml.sluzbenik.model.vakcina.Vakcina;
import com.xml.sluzbenik.repository.VakcinaRepository;
import com.xml.sluzbenik.service.VakcinaService;

@Service
public class VakcinaServiceImpl implements VakcinaService {
	
	@Autowired
	private VakcinaRepository vakcinaRepository;

	@Override
	public List<Vakcina> dobaviSve() throws Exception {
		List<Vakcina> vakcineDb = vakcinaRepository.pronadjiSve();
		if (vakcineDb == null || vakcineDb.size() != Proizvodjac.values().length) {
			return dodajVakcine();
		}
		return vakcineDb;
	}
	
	private List<Vakcina> dodajVakcine() throws Exception {
		List<Vakcina> vakcine = new ArrayList<Vakcina>();
		for (Proizvodjac p : Proizvodjac.values()) {
			Vakcina vakcina = new Vakcina();
			vakcina.setNaziv(p);
			vakcina.setKolicina(1000);
			vakcinaRepository.saveVakcinaObjekat(vakcina);
			vakcine.add(vakcina);
		}
		return vakcine;
	}

	@Override
	public void azurirajKolicinu(Vakcina vakcina) throws Exception {
		vakcinaRepository.saveVakcinaObjekat(vakcina);
	}
	
	@Override
	public void smanjiKolicinu(String nazivVakcine) throws Exception {
		List<Vakcina> vakcineDb = vakcinaRepository.pronadjiSve();
		if (vakcineDb == null || vakcineDb.size() != Proizvodjac.values().length) {
			dodajVakcine();
		}
		Vakcina vakcina = vakcinaRepository.pronadjiVakcinu(nazivVakcine);
		vakcina.setKolicina(vakcina.getKolicina() - 1);
		vakcinaRepository.saveVakcinaObjekat(vakcina);
	}
}
