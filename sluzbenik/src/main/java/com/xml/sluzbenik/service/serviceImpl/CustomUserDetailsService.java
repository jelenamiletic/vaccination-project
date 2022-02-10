package com.xml.sluzbenik.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.model.PunoIme;
import com.xml.sluzbenik.model.Role;
import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;
import com.xml.sluzbenik.repository.SluzbenikRepository;
import com.xml.sluzbenik.utils.RoleKonstante;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SluzbenikRepository sluzbenikRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) {
		try {
			List<Sluzbenik> sluzbenici = sluzbenikRepository.pronadjiSve();
			if (sluzbenici == null || sluzbenici.isEmpty()) {
				PunoIme punoIme = new PunoIme();
				Sluzbenik sluzbenik = new Sluzbenik();
				sluzbenik.setPunoIme(punoIme);
				punoIme.setIme("Markuza");
				punoIme.setPrezime("Petruza");
				sluzbenik.setEmail("sluzbenik@gmail.com");
				sluzbenik.setLozinka(passwordEncoder.encode("123"));
				List<Role> roles = new ArrayList<Role>();
				Role role = new Role();
				role.setRoleName(RoleKonstante.ROLE_SLUZBENIK);
				roles.add(role);
				sluzbenik.setEnabled(true);
				sluzbenik.setRoles(roles);
				sluzbenikRepository.saveSluzbenikObjekat(sluzbenik);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Sluzbenik sluzbenik = null;
		try {
			sluzbenik = sluzbenikRepository.pronadjiSluzbenika(email.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sluzbenik;
	}
}
