package com.xml.sluzbenik.service;

import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;

public interface SluzbenikService {

	Sluzbenik pronadjiSluzbenika(String email) throws Exception;
}
