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

import com.xml.vakcinacija.model.potvrda.ListaPotvrda;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.service.PotvrdaService;

@RestController
@RequestMapping("/potvrda")
public class PotvrdaController {

	@Autowired
	PotvrdaService potvrdaService;
	
	@PostMapping(value = "/dodajNovuPotvrdu", consumes = MediaType.APPLICATION_XML_VALUE)
	public void dodajNovuPotvrdu(@RequestBody String PotvrdaXML) throws Exception {
		potvrdaService.dodajNoviPotvrda(PotvrdaXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ListaPotvrda> pronadjiSve() throws Exception {
		ListaPotvrda lista = new ListaPotvrda();
		lista.setPotvrda(potvrdaService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiPotvrdaPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<Potvrda> pronadjiPotvrdaPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(potvrdaService.pronadjiPotvrdaPoJmbg(jmbg, brojDoze), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeXmlPoJmbg/{jmbg}/{brojDoze}")
	public void nabaviMetaPodatkeXmlPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws IOException {
		potvrdaService.nabaviMetaPodatkeXmlPoJmbg(jmbg, brojDoze);
	}
}
