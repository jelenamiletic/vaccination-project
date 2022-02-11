package com.xml.sluzbenik.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.sluzbenik.model.Period;
import com.xml.sluzbenik.model.izvestaj.Izvestaj;
import com.xml.sluzbenik.model.izvestaj.ListaIzvestaja;
import com.xml.sluzbenik.service.IzvestajService;

@RestController
@RequestMapping("/izvestaj")
public class IzvestajController {

	@Autowired
	private IzvestajService izvestajService;

	@PostMapping(value = "/dodajNoviIzvestaj", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<Izvestaj> dodajNoviIzvestaj(@RequestBody Period period) throws Exception {
		return new ResponseEntity<>(izvestajService.dodajNoviIzvestaj(period), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaIzvestaja> pronadjiSve() throws Exception {
		ListaIzvestaja lista = new ListaIzvestaja();
		lista.setIzvestaj(izvestajService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiIzvestaj/{odDatum}/{doDatum}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<Izvestaj> pronadjiIzvestaj(@PathVariable String odDatum, @PathVariable String doDatum) throws Exception {
		return new ResponseEntity<>(izvestajService.pronadjiIzvestaj(odDatum, doDatum), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeXmlPoDatumima/{odDatum}/{doDatum}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public void nabaviMetaPodatkeXmlPoDatumima(@PathVariable String odDatum, @PathVariable String doDatum) throws IOException {
		izvestajService.nabaviMetaPodatkeXmlPoDatumima(odDatum, doDatum);
	}
	
	@GetMapping(value = "/generisiPdf/{odDatum}/{doDatum}", produces = MediaType.APPLICATION_PDF_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiPdf(@PathVariable String odDatum, @PathVariable String doDatum) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(izvestajService.generisiPdf(odDatum, doDatum)), HttpStatus.OK);
	}
}
