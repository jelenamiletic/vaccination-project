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
}
