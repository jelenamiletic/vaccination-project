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

import com.xml.vakcinacija.model.izvestaj.Izvestaj;
import com.xml.vakcinacija.model.izvestaj.ListaIzvestaja;
import com.xml.vakcinacija.service.IzvestajService;

@RestController
@RequestMapping("/izvestaj")
public class IzvestajController {

	@Autowired
	private IzvestajService izvestajService;

	@PostMapping(value = "/dodajNoviIzvestaj", consumes = MediaType.APPLICATION_XML_VALUE)
	public void dodajNoviIzvestaj(@RequestBody String izvestajXML) throws Exception {
		izvestajService.dodajNoviIzvestaj(izvestajXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ListaIzvestaja> pronadjiSve() throws Exception {
		ListaIzvestaja lista = new ListaIzvestaja();
		lista.setIzvestaj(izvestajService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiIzvestaj/{odDatum}/{doDatum}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Izvestaj> pronadjiIzvestaj(@PathVariable String odDatum, @PathVariable String doDatum) throws Exception {
		return new ResponseEntity<>(izvestajService.pronadjiIzvestaj(odDatum, doDatum), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeXmlPoDatumima/{odDatum}/{doDatum}")
	public void nabaviMetaPodatkeXmlPoDatumima(@PathVariable String odDatum, @PathVariable String doDatum) throws IOException {
		izvestajService.nabaviMetaPodatkeXmlPoDatumima(odDatum, doDatum);
	}
}
