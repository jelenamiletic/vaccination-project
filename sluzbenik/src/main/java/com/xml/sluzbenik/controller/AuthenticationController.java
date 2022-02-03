package com.xml.sluzbenik.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.sluzbenik.model.PunoIme;
import com.xml.sluzbenik.model.Role;
import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;
import com.xml.sluzbenik.repository.SluzbenikRepository;
import com.xml.sluzbenik.security.JwtAuthenticationRequest;
import com.xml.sluzbenik.security.UserTokenState;
import com.xml.sluzbenik.utils.RoleKonstante;
import com.xml.sluzbenik.utils.TokenUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SluzbenikRepository sluzbenikRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername().toLowerCase(), authenticationRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			Sluzbenik sluzbenik = (Sluzbenik) authentication.getPrincipal();
			String jwt = tokenUtils.generateToken(sluzbenik.getUsername());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, sluzbenik.getUsername(), sluzbenik.getRoles().get(0).getRoleName()));
		} catch (DisabledException de) {
			return new ResponseEntity<UserTokenState>(HttpStatus.FORBIDDEN);
		} catch (BadCredentialsException bdc) {
			return new ResponseEntity<UserTokenState>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/kreirajPrvogSluzbenika")
	public void kreirajPrvogSluzbenika() throws Exception {
		List<Sluzbenik> sluzbenici = (List<Sluzbenik>) sluzbenikRepository.pronadjiSve();
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
}
