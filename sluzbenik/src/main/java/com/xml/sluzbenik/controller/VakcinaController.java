package com.xml.sluzbenik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.sluzbenik.model.vakcina.ListaVakcina;
import com.xml.sluzbenik.model.vakcina.Vakcina;
import com.xml.sluzbenik.service.VakcinaService;

@RestController
@RequestMapping("/vakcina")
public class VakcinaController {
	
	@Autowired
	private VakcinaService vakcinaService;

	@GetMapping(value = "/dobaviSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaVakcina> dobaviSve() throws Exception {
		ListaVakcina lista = new ListaVakcina();
		lista.setVakcina(vakcinaService.dobaviSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@PutMapping(value = "/azurirajKolicinu", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public void azurirajKolicinu(@RequestBody Vakcina vakcina) throws Exception {
		vakcinaService.azurirajKolicinu(vakcina);
	}
	
	@PutMapping(value = "/smanjiKolicinu/{nazivVakcine}")
	@PreAuthorize("hasRole('ROLE_ZDRAVSTVENI_RADNIK')")
	public void smanjiKolicinu(@PathVariable String nazivVakcine) throws Exception {
		vakcinaService.smanjiKolicinu(nazivVakcine);
	}
}
