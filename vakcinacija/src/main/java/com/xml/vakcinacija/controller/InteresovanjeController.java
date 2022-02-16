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

import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.model.interesovanje.ListaInteresovanja;
import com.xml.vakcinacija.service.InteresovanjeService;

@RestController
@RequestMapping("/interesovanje")
public class InteresovanjeController {
	
	@Autowired
	private InteresovanjeService interesovanjeService;

	@PostMapping(value = "/dodajNovoInteresovanje", consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public void dodajNovoInteresovanje(@RequestBody String interesovanjeXML) throws Exception {
		interesovanjeService.dodajNovoInteresovanje(interesovanjeXML);
	}
	
	@GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<ListaInteresovanja> pronadjiSve() throws Exception {
		ListaInteresovanja lista = new ListaInteresovanja();
		lista.setInteresovanje(interesovanjeService.pronadjiSve());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pronadjiSveOsnovnaPretraga/{pretraga}")
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> pronadjiSveOsnovnaPretraga(@PathVariable String pretraga) throws Exception {
		return new ResponseEntity<>(interesovanjeService.pronadjiSveOsnovnaPretraga(pretraga), HttpStatus.OK);
	}

	@GetMapping(value = "/pronadjiInteresovanjePoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<Interesovanje> pronadjiInteresovanjePoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(interesovanjeService.pronadjiInteresovanjePoJmbg(jmbg), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeJSONPoJmbg/{jmbg}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeJSONPoJmbg(@PathVariable String jmbg) throws IOException {
		return new ResponseEntity<>(new InputStreamResource(interesovanjeService.nabaviMetaPodatkeJSONPoJmbg(jmbg)), HttpStatus.OK); 
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeRDFPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeRDFPoJmbg(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(interesovanjeService.nabaviMetaPodatkeRDFPoJmbg(jmbg)), HttpStatus.OK); 
	}
	
	@GetMapping(value = "/generisiXhtml/{jmbg}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiXHTML(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(interesovanjeService.generisiXHTML(jmbg)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/generisiPdf/{jmbg}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> generisiPdf(@PathVariable String jmbg) throws Exception {
		return new ResponseEntity<>(new InputStreamResource(interesovanjeService.generisiPdf(jmbg)), HttpStatus.OK);
	}
}
