package com.xml.vakcinacija.service;

import com.xml.vakcinacija.model.gradjanin.Gradjanin;

public interface GradjaninService {

	void registracija(Gradjanin gradjanin) throws Exception;
	
	Gradjanin poEmail(String email) throws Exception;
}
