package com.xml.vakcinacija.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.KorisnikPostojiException;
import com.xml.vakcinacija.model.Role;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.repository.KorisnikRepository;
import com.xml.vakcinacija.service.GradjaninService;
import com.xml.vakcinacija.utils.RoleKonstante;

@Service
public class GradjaninServiceImpl implements GradjaninService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void registracija(Gradjanin gradjanin) throws Exception {
		Gradjanin postoji = korisnikRepository.pronadjiGradjanina(gradjanin.getEmail().toLowerCase(), gradjanin.getJMBG());
		if (postoji != null) {
			throw new KorisnikPostojiException();
		}
		gradjanin.setEmail(gradjanin.getEmail().toLowerCase());
		gradjanin.setLozinka(passwordEncoder.encode(gradjanin.getLozinka()));
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleName(RoleKonstante.ROLE_GRADJANIN);
		roles.add(role);
		gradjanin.setEnabled(true);
		gradjanin.setRoles(roles);
		korisnikRepository.saveKorisnikObjekat(gradjanin);
	}

	@Override
	public Gradjanin poEmail(String email) throws Exception {
		return korisnikRepository.pronadjiGradjaninaEmail(email);
	}
}
