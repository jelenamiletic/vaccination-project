package com.xml.vakcinacija.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.saglasnost.ListaSaglasnosti;
import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.service.SaglasnostService;

@RestController
@RequestMapping("/saglasnost")
public class SaglasnostController {
	
	@Autowired
	private SaglasnostService saglasnostService;

	@PostMapping(value = "/dodajNovuSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
	public void dodajNovuSaglasnost(@RequestBody String XML) throws Exception {
		saglasnostService.dodajNovuSaglasnost(XML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ListaSaglasnosti> pronadjiSve() throws Exception {
		ListaSaglasnosti lista = new ListaSaglasnosti();
		lista.setSaglasnost(saglasnostService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiSaglasnostPoJmbgIliBrPasosa/{id}", produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<Saglasnost> pronadjiSaglasnostPoJmbg(@PathVariable String id) throws Exception {
		return new ResponseEntity<>(saglasnostService.pronadjiSaglasnostPoJmbgIliBrPasosa(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeXmlPoId/{id}")
	public void nabaviMetaPodatkeXmlPoJmbg(@PathVariable String id) throws IOException {
		saglasnostService.nabaviMetaPodatkeXmlPoId(id);
	}
}