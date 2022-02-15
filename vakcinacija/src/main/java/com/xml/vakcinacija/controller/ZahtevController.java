package com.xml.vakcinacija.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.OdgovorNaZahtev;
import com.xml.vakcinacija.model.zahtev.ListaZahteva;
import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.service.ZahtevService;

@RestController
@RequestMapping("/zahtev")
public class ZahtevController {

	@Autowired
	private ZahtevService zahtevService;
	
	@PostMapping(value = "/dodajNoviZahtev", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public void dodajNoviZahtev(@RequestBody String zahtevXML) throws Exception {
		zahtevService.dodajNoviZahtev(zahtevXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaZahteva> pronadjiSve() throws Exception {
		ListaZahteva lista = new ListaZahteva();
		lista.setZahtev(zahtevService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiZahtevPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<Zahtev> pronadjiZahtevPoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(zahtevService.pronadjiZahtevPoJmbg(jmbg), HttpStatus.OK);
	}
	
	@GetMapping(value = "/dobaviSveNeodobreneZahteve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaZahteva> dobaviSveNeodobreneZahteve() throws Exception {
		ListaZahteva lista = new ListaZahteva();
		lista.setZahtev(zahtevService.dobaviSveNeodobreneZahteve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PutMapping(value = "/promeniStatusZahteva")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public void promeniStatusZahteva(@RequestBody OdgovorNaZahtev odgovorNaZahtev) throws Exception {
		zahtevService.promeniStatusZahteva(odgovorNaZahtev);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeJSONPoJmbg/{jmbg}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeJSONPoJmbg(@PathVariable String jmbg) throws IOException {
		return new ResponseEntity<>(new InputStreamResource(zahtevService.nabaviMetaPodatkeJSONPoJmbg(jmbg)), HttpStatus.OK);
	}
}
