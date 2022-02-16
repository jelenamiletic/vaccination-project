package com.xml.vakcinacija.controller;

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
import org.xml.sax.SAXException;

import com.xml.vakcinacija.model.saglasnost.ListaSaglasnosti;
import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.service.SaglasnostService;

@RestController
@RequestMapping("/saglasnost")
public class SaglasnostController {
	
	@Autowired
	private SaglasnostService saglasnostService;

	@PostMapping(value = "/dodajNovuSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public void dodajNovuSaglasnost(@RequestBody String XML) throws Exception {
		saglasnostService.dodajNovuSaglasnost(XML);
	}
	
	@PutMapping(value = "/promeniSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_ZDRAVSTVENI_RADNIK')")
	public void promeniSaglasnost(@RequestBody String XML) throws Exception {
		saglasnostService.izmeniSaglasnost(XML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_SLUZBENIK', 'ROLE_ZDRAVSTVENI_RADNIK')")
	public ResponseEntity<ListaSaglasnosti> pronadjiSve() throws Exception {
		ListaSaglasnosti lista = new ListaSaglasnosti();
		lista.setSaglasnost(saglasnostService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveOsnovnaPretraga/{pretraga}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> pronadjiSveOsnovnaPretraga(@PathVariable String pretraga) throws Exception {
		return new ResponseEntity<>(saglasnostService.pronadjiSveOsnovnaPretraga(pretraga), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveNaprednaPretraga/{ime}/{prezime}/{jmbg}/{pol}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> pronadjiSveNaprednaPretraga(@PathVariable String ime, @PathVariable String prezime,
			@PathVariable String jmbg, @PathVariable String pol) throws Exception {
		return new ResponseEntity<>(saglasnostService.pronadjiSveNaprednaPretraga(ime, prezime, jmbg, pol), HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiSaglasnostPoJmbgIliBrPasosa/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK', 'ROLE_ZDRAVSTVENI_RADNIK')")
	public ResponseEntity<ListaSaglasnosti> pronadjiSaglasnostPoJmbg(@PathVariable String id) throws Exception {
		ListaSaglasnosti lista = new ListaSaglasnosti();
		lista.setSaglasnost(saglasnostService.pronadjiSaglasnostPoJmbgIliBrPasosa(id));
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_ZDRAVSTVENI_RADNIK')")
	public ResponseEntity<Saglasnost> pronadjiNajnovijuSaglasnostPoJmbg(@PathVariable String id) throws Exception {
		return new ResponseEntity<>(saglasnostService.pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiNajnovijuPunuSaglasnostPoJmbgIliBrPasosa/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK', 'ROLE_ZDRAVSTVENI_RADNIK')")
	public ResponseEntity<Saglasnost> pronadjiNajnovijuPunuSaglasnostPoJmbg(@PathVariable String id) throws Exception {
		return new ResponseEntity<>(saglasnostService.pronadjiNajnovijuPunuSaglasnostPoJmbgIliBrPasosa(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeJSONPoId/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeJSONPoId(@PathVariable String jmbg, @PathVariable int brojDoze) throws SAXException, Exception {
		return new ResponseEntity<>(new InputStreamResource(saglasnostService.nabaviMetaPodatkeJSONPoId(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeRDFPoId/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeRDFPoId(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(saglasnostService.nabaviMetaPodatkeRDFPoId(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/generisiXhtml/{jmbg}/{brojDoze}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiXHTML(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(saglasnostService.generisiXhtml(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/generisiPdf/{jmbg}/{brojDoze}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiPdf(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(saglasnostService.generisiPdf(jmbg, brojDoze)), HttpStatus.OK);
	}
}