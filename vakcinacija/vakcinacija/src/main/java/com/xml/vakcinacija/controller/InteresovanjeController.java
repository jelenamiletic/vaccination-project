package com.xml.vakcinacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.service.InteresovanjeService;

@RestController
@RequestMapping("/interesovanje")
public class InteresovanjeController {
	
	@Autowired
	private InteresovanjeService interesovanjeService;

	@PostMapping(value = "/dodajNovoInteresovanje")
	public void dodajNovoInteresovanje(@RequestBody String interesovanjeXML) throws Exception {
		interesovanjeService.dodajNovoInteresovanje(interesovanjeXML);
	}
}
