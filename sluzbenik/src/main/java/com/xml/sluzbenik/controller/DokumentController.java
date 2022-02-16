package com.xml.sluzbenik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xml.sluzbenik.security.TokenBasedAuthentication;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@RestController
@RequestMapping("/dokumenti")
public class DokumentController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MarshallerService marshallerService;

	@GetMapping(value = "/saglasnostGenerisiXhtml/{jmbg}/{brojDoze}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> saglasnostGenerisiXHTML(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/saglasnost/generisiXhtml/"+ jmbg + "/" + brojDoze, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/saglasnostGenerisiPdf/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_PDF_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> saglasnostGenerisiPdf(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/saglasnost/generisiPdf/"+ jmbg + "/" + brojDoze, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/potvrdaGenerisiXhtml/{jmbg}/{brojDoze}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> potvrdaGenerisiXHTML(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/potvrda/generisiXhtml/"+ jmbg + "/" + brojDoze, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/potvrdaGenerisiPdf/{jmbg}/{brojDoze}", produces = MediaType.APPLICATION_PDF_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> potvrdaGenerisiPdf(@PathVariable String jmbg, @PathVariable int brojDoze) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/potvrda/generisiPdf/"+ jmbg + "/" + brojDoze, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/sertifikatGenerisiXhtml/{jmbg}", produces = MediaType.TEXT_HTML_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> sertifikatGenerisiXHTML(@PathVariable String jmbg) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/sertifikat/generisiXhtml/"+ jmbg, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/sertifikatGenerisiPdf/{jmbg}", produces = MediaType.APPLICATION_PDF_VALUE)
	@PreAuthorize("hasRole('ROLE_SLUZBENIK')")
	public ResponseEntity<InputStreamResource> sertifikatGenerisiPdf(@PathVariable String jmbg) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<Resource> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/sertifikat/generisiPdf/"+ jmbg, HttpMethod.GET, new HttpEntity<Object>(headers), Resource.class);
		
		return new ResponseEntity<>(new InputStreamResource(saglasnosti.getBody().getInputStream()), HttpStatus.OK);
	}
}
