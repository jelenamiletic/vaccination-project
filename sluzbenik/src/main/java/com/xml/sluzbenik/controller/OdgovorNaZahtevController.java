package com.xml.sluzbenik.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.xml.sax.SAXException;

import com.xml.sluzbenik.model.OdgovorNaZahtev;
import com.xml.sluzbenik.model.potvrda.Potvrda;
import com.xml.sluzbenik.model.sertifikat.Sertifikat;
import com.xml.sluzbenik.model.zahtev.ListaZahteva;
import com.xml.sluzbenik.service.OdgovorNaZahtevService;

@RestController
@RequestMapping("/odgovorNaZahtev")
public class OdgovorNaZahtevController {

	@Autowired
	private OdgovorNaZahtevService odgovorNaZahtevService;
	
	@GetMapping(value = "/dobaviSveNeodobreneZahteve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaZahteva> dobaviSveNeodobreneZahteve() throws SAXException {
		return new ResponseEntity<>(odgovorNaZahtevService.dobaviSveNeodobreneZahteve(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/promeniStatusZahteva", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public void promeniStatusZahteva(@RequestBody OdgovorNaZahtev odgovorNaZahtev) throws SAXException {
		odgovorNaZahtevService.promeniStatusZahteva(odgovorNaZahtev);
	}
	
	@GetMapping(value = "/dobaviPoslednjuPotvrduPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<Potvrda> dobaviPoslednjuPotvrduPoJmbg(@PathVariable String jmbg) throws SAXException {
		return new ResponseEntity<>(odgovorNaZahtevService.dobaviPoslednjuPotvrduPoJmbg(jmbg), HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajNoviSertifikat", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public void dodajNoviSertifikat(@RequestBody Sertifikat sertifikat) throws SAXException {
		odgovorNaZahtevService.dodajNoviSertifikat(sertifikat);
	}
}
