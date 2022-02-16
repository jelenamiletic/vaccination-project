package com.xml.sluzbenik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import com.xml.sluzbenik.service.PretragaService;

@RestController
@RequestMapping("/pretraga")
public class PretragaController {

	@Autowired
	private PretragaService pretragaService;
	
	@GetMapping(value = "/osnovnaPretraga/{pretragaDokumenata}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> osnovnaPretraga(@PathVariable String pretragaDokumenata) throws Exception {
		return new ResponseEntity<>(pretragaService.osnovnaPretraga(pretragaDokumenata), HttpStatus.OK);
	}
	
	@PutMapping(value = "/naprednaPretraga", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<String> naprednaPretraga(@RequestBody String pretragaDokumenata) throws Exception {
		return new ResponseEntity<>(pretragaService.naprednaPretraga(pretragaDokumenata), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkePotvrdaRDFPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkePotvrdaRDFPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(pretragaService.nabaviMetaPodatkePotvrdaRDFPoJmbg(jmbg, brojDoze), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkePotvrdaJSONPoJmbg/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkePotvrdaJSONPoJmbg(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(pretragaService.nabaviMetaPodatkePotvrdaJSONPoJmbg(jmbg, brojDoze), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeSaglasnostRDFPoId/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeSaglasnostRDFPoId(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(pretragaService.nabaviMetaPodatkePotvrdaRDFPoJmbg(jmbg, brojDoze), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nabaviMetaPodatkeSaglasnostJSONPoId/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> nabaviMetaPodatkeSaglasnostJSONPoId(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		return new ResponseEntity<>(pretragaService.nabaviMetaPodatkeSaglasnostJSONPoId(jmbg, brojDoze), HttpStatus.OK);
	}
}
