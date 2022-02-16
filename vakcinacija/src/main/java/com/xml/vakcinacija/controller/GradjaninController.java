package com.xml.vakcinacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.service.GradjaninService;

@RestController
@RequestMapping("/gradjanin")
public class GradjaninController {

	@Autowired
	private GradjaninService gradjaninService;
	
	@PostMapping(value = "/registracija", consumes = MediaType.APPLICATION_XML_VALUE)
	public void registracija(@RequestBody Gradjanin gradjanin) throws Exception {
		gradjaninService.registracija(gradjanin);
	}
	
	@GetMapping(value = "/getUlogovaniGradjanin", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_GRADJANIN')")
	public Gradjanin osnovnaPretraga() throws Exception {
		return (Gradjanin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@GetMapping(value = "/getGradjaninPoEmail/{email}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_ZDRAVSTVENI_RADNIK')")
	public Gradjanin gradjaninPoEmail(@PathVariable String email) throws Exception {
		return gradjaninService.poEmail(email);
	}
}
