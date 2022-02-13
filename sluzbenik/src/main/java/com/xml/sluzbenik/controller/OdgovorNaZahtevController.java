package com.xml.sluzbenik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.sluzbenik.model.zahtev.ListaZahteva;
import com.xml.sluzbenik.service.OdgovorNaZahtevService;

@RestController
@RequestMapping("/odgovorNaZahtev")
public class OdgovorNaZahtevController {

	@Autowired
	private OdgovorNaZahtevService odgovorNaZahtevService;
	
	@GetMapping(value = "/dobaviSveNeodobreneZahteve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaZahteva> dobaviSveNeodobreneZahteve() {
		
	}
}
