package com.xml.vakcinacija.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.vakcinacija.model.Korisnik;
import com.xml.vakcinacija.security.JwtAuthenticationRequest;
import com.xml.vakcinacija.security.Token;
import com.xml.vakcinacija.security.TokenUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Token> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail().toLowerCase(), authenticationRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			Korisnik korisnik = (Korisnik) authentication.getPrincipal();
			String jwt = tokenUtils.generateToken(korisnik.getUsername());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new Token(jwt, expiresIn, korisnik.getUsername(), korisnik.getRoles().get(0).getRoleName()));
		} catch (DisabledException de) {
			return new ResponseEntity<Token>(HttpStatus.FORBIDDEN);
		} catch (BadCredentialsException bdc) {
			return new ResponseEntity<Token>(HttpStatus.NOT_FOUND);
		}
	}
}
