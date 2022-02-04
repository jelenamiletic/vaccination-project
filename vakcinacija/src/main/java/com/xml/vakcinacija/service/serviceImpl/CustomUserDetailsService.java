package com.xml.vakcinacija.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.Role;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.zdravstveni_radnik.ZdravstveniRadnik;
import com.xml.vakcinacija.utils.RoleKonstante;
import com.xml.vakcinacija.repository.KorisnikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		try {
			List<ZdravstveniRadnik> zdravstveniRadnici = korisnikRepository.pronadjiSveZdravstveneRadnike();
			if (zdravstveniRadnici == null || zdravstveniRadnici.isEmpty()) {
				PunoIme punoIme = new PunoIme();
				punoIme.setIme("Petar");
				punoIme.setPrezime("Petruza");
				ZdravstveniRadnik zdravstveniRadnik = new ZdravstveniRadnik();
				zdravstveniRadnik.setPunoIme(punoIme);
				zdravstveniRadnik.setEmail("zdrav@gmail.com");
				zdravstveniRadnik.setLozinka(passwordEncoder.encode("123"));
				List<Role> roles = new ArrayList<Role>();
				Role role = new Role();
				role.setRoleName(RoleKonstante.ROLE_ZDRAVSTVENI_RADNIK);
				roles.add(role);
				zdravstveniRadnik.setEnabled(true);
				zdravstveniRadnik.setRoles(roles);
				zdravstveniRadnik.setZdravstvenaUstanova("Ustanova");
				zdravstveniRadnik.setPunkt(1);
				korisnikRepository.saveKorisnikObjekat(zdravstveniRadnik);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Gradjanin gradjanin = null;
		// Gradjanin
		try {
			gradjanin = korisnikRepository.pronadjiGradjanina(email.toLowerCase());
			if (gradjanin != null) {
				return gradjanin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Zdravstevni radnik
		ZdravstveniRadnik zdravstveniRadnik = null;
		try {
			zdravstveniRadnik = korisnikRepository.pronadjiZdravstevnogRadnika(email.toLowerCase());
			if (zdravstveniRadnik != null) {
				return zdravstveniRadnik;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
