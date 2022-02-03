package com.xml.sluzbenik.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;
import com.xml.sluzbenik.repository.SluzbenikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SluzbenikRepository sluzbenikRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Sluzbenik sluzbenik = null;
		try {
			sluzbenik = sluzbenikRepository.pronadjiSluzbenika(email.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sluzbenik == null) {
			throw new UsernameNotFoundException(String.format("Sluzbenik sa email-om '%s' nije pronadjen.", sluzbenik));
		} else {
			return sluzbenik;
		}
	}
}
