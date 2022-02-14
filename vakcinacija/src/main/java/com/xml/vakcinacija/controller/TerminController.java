package com.xml.vakcinacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.termin.ListaTermina;
import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.service.TerminService;

@RestController
@RequestMapping("/termin")
public class TerminController {

	@Autowired
	private TerminService terminService;
	
	@GetMapping(value = "/pronadjiAktuelniTerminPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public ResponseEntity<Termin> getAktuelniTermin(@PathVariable String jmbg) throws Exception{
		return new ResponseEntity<>(terminService.getAktuelniTerminPoJmbg(jmbg), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveTerminePoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public ResponseEntity<ListaTermina> getSveTerminiPoJmbg(@PathVariable String jmbg) throws Exception{
		ListaTermina lista = new ListaTermina();
		lista.setTermin(terminService.getTerminePoJmbg(jmbg));
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveTermine", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public ResponseEntity<ListaTermina> getSveTermini() throws Exception{
		ListaTermina lista = new ListaTermina();
		lista.setTermin(terminService.getSveTermine());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
}
