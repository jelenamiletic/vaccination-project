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

import com.xml.vakcinacija.model.zahtev.ListaZahteva;
import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.service.ZahtevService;

@RestController
@RequestMapping("/zahtev")
public class ZahtevController {

	@Autowired
	ZahtevService zahtevService;
	
	@PostMapping(value = "/dodajNoviZahtev", consumes = MediaType.APPLICATION_XML_VALUE)
	public void dodajNoviZahtev(@RequestBody String zahtevXML) throws Exception {
		zahtevService.dodajNoviZahtev(zahtevXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ListaZahteva> pronadjiSve() throws Exception {
		ListaZahteva lista = new ListaZahteva();
		lista.setZahtev(zahtevService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiZahtevPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<Zahtev> pronadjiZahtevPoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(zahtevService.pronadjiZahtevPoJmbg(jmbg), HttpStatus.OK);
	}
}
