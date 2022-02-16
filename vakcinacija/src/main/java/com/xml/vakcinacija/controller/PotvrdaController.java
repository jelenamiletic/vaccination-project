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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.potvrda.ListaPotvrda;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.service.PotvrdaService;

@RestController
@RequestMapping("/potvrda")
public class PotvrdaController {

	@Autowired
	PotvrdaService potvrdaService;
	
	@PostMapping(value = "/dodajNovuPotvrdu", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_ZDRAVSTVENI_RADNIK')")
	public void dodajNovuPotvrdu(@RequestBody String PotvrdaXML) throws Exception {
		potvrdaService.dodajNoviPotvrda(PotvrdaXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaPotvrda> pronadjiSve() throws Exception {
		ListaPotvrda lista = new ListaPotvrda();
		lista.setPotvrda(potvrdaService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveOsnovnaPretraga/{pretraga}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> pronadjiSveOsnovnaPretraga(@PathVariable String pretraga) throws Exception {
		return new ResponseEntity<>(potvrdaService.pronadjiSveOsnovnaPretraga(pretraga), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveNaprednaPretraga/{ime}/{prezime}/{jmbg}/{pol}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> pronadjiSveNaprednaPretraga(@PathVariable String ime, @PathVariable String prezime,
			@PathVariable String jmbg, @PathVariable String pol) throws Exception {
		return new ResponseEntity<>(potvrdaService.pronadjiSveNaprednaPretraga(ime, prezime, jmbg, pol), HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiPotvrdaPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<Potvrda> pronadjiPotvrdaPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(potvrdaService.pronadjiPotvrdaPoJmbg(jmbg, brojDoze), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeJSONPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeJSONPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws IOException {
		return new ResponseEntity<>(new InputStreamResource(potvrdaService.nabaviMetaPodatkeJSONPoJmbg(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeRDFPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeRDFPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(potvrdaService.nabaviMetaPodatkeRDFPoJmbg(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/dobaviPoslednjuPotvrduPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<Potvrda> dobaviPoslednjuPotvrduPoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(potvrdaService.dobaviPoslednjuPotvrduPoJmbg(jmbg), HttpStatus.OK);
	}
	
	@GetMapping(value = "/generisiXhtml/{jmbg}/{brojDoze}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiXHTML(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(potvrdaService.generisiXHTML(jmbg, brojDoze)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/generisiPdf/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> generisiPdf(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(potvrdaService.generisiPdf(jmbg, brojDoze)), HttpStatus.OK);
	}
}
