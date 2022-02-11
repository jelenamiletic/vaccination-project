package com.xml.sluzbenik.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.model.vakcina.Vakcina;
import com.xml.sluzbenik.repository.VakcinaRepository;
import com.xml.sluzbenik.service.VakcinaService;

@Service
public class VakcinaServiceImpl implements VakcinaService {
	
	@Autowired
	private VakcinaRepository vakcinaRepository;

	@Override
	public List<Vakcina> dobaviSve() throws Exception {
		return vakcinaRepository.pronadjiSve();
	}
	
	@Override
	public void azurirajKolicinu(Vakcina vakcina) throws Exception {
		vakcinaRepository.saveVakcinaObjekat(vakcina);
	}
	
	@Override
	public void smanjiKolicinu(String nazivVakcine) throws Exception {
		Vakcina vakcina = vakcinaRepository.pronadjiVakcinu(nazivVakcine);
		vakcina.setKolicina(vakcina.getKolicina() - 1);
		vakcinaRepository.saveVakcinaObjekat(vakcina);
	}
}
