package com.xml.vakcinacija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
}
