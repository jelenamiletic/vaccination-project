package com.xml.sluzbenik.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;
import com.xml.sluzbenik.repository.SluzbenikRepository;
import com.xml.sluzbenik.service.SluzbenikService;

@Service
public class SluzbenikServiceImpl implements SluzbenikService {
	
	@Autowired
	private SluzbenikRepository sluzbenikRepository;

	@Override
	public Sluzbenik pronadjiSluzbenika(String email) throws Exception {
		return sluzbenikRepository.pronadjiSluzbenika(email);
	}

}
