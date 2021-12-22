package com.xml.vakcinacija.controller;

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

import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.model.interesovanje.ListaInteresovanja;
import com.xml.vakcinacija.service.InteresovanjeService;

@RestController
@RequestMapping("/interesovanje")
public class InteresovanjeController {
	
	@Autowired
	private InteresovanjeService interesovanjeService;

	@PostMapping(value = "/dodajNovoInteresovanje", consumes = MediaType.APPLICATION_XML_VALUE)
	public void dodajNovoInteresovanje(@RequestBody String interesovanjeXML) throws Exception {
		interesovanjeService.dodajNovoInteresovanje(interesovanjeXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ListaInteresovanja> pronadjiSve() throws Exception {
		ListaInteresovanja lista = new ListaInteresovanja();
		lista.setInteresovanje(interesovanjeService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiInteresovanjePoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<Interesovanje> pronadjiInteresovanjePoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(interesovanjeService.pronadjiInteresovanjePoJmbg(jmbg), HttpStatus.OK);
	}
}
