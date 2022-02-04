package com.xml.vakcinacija.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xml.vakcinacija.model.sluzbenik.Sluzbenik;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import io.jsonwebtoken.ExpiredJwtException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);
		
		String sluzbenikXml = request.getHeader("Sluzbenik");
		Sluzbenik sluzbenik = null;
		if (sluzbenikXml != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(Sluzbenik.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				sluzbenik = (Sluzbenik) unmarshaller.unmarshal(new ByteArrayInputStream(sluzbenikXml.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		UserDetails sluzbenikUserDetails = null;
		if (sluzbenik != null) {
			sluzbenikUserDetails = (UserDetails) sluzbenik;
		}
		
		try {
	
			if (authToken != null) {
				username = tokenUtils.getUsernameFromToken(authToken);
				
				if (username != null) {
					
					UserDetails userDetails = null;
					if (sluzbenikUserDetails == null) {
						userDetails = userDetailsService.loadUserByUsername(username);
						
						if (userDetails == null) {
							throw new UsernameNotFoundException(String.format("Korisnik sa email-om '%s' nije pronadjen.", username));
						}
					} else {
						userDetails = sluzbenikUserDetails;
					}
						
					if (tokenUtils.validateToken(authToken, userDetails)) {
						
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			
		} catch (ExpiredJwtException ex) {
			LOGGER.debug("Token expired!");
		} 
		chain.doFilter(request, response);
	}

}
