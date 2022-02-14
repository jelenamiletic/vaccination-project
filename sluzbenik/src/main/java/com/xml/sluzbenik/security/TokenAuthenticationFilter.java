package com.xml.sluzbenik.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xml.sluzbenik.model.gradjanin.Gradjanin;
import com.xml.sluzbenik.model.zdravstveni_radnik.ZdravstveniRadnik;

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
		
		String zdravstveniRadnikXml = request.getHeader("ZdravstveniRadnik");
		String gradjaninXml = request.getHeader("Gradjanin");
		ZdravstveniRadnik zr = null;
		Gradjanin g = null;
		if (zdravstveniRadnikXml != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(ZdravstveniRadnik.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				zr = (ZdravstveniRadnik) unmarshaller.unmarshal(new ByteArrayInputStream(zdravstveniRadnikXml.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (gradjaninXml != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(Gradjanin.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				g = (Gradjanin) unmarshaller.unmarshal(new ByteArrayInputStream(gradjaninXml.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		UserDetails zrUserDetails = null;
		UserDetails gUserDetails = null;
		if (zr != null) {
			zrUserDetails = (UserDetails) zr;
		}
		
		if (g != null) {
			gUserDetails = (UserDetails) g;
		}
		

		try {
	
			if (authToken != null) {
				username = tokenUtils.getUsernameFromToken(authToken);
				
				if (username != null) {
					
					UserDetails userDetails = null;
					if (zrUserDetails == null && gUserDetails == null) {
						userDetails = userDetailsService.loadUserByUsername(username);
						
						if (userDetails == null) {
							throw new UsernameNotFoundException(String.format("Korisnik sa email-om '%s' nije pronadjen.", username));
						}
					} else {
						if (zrUserDetails == null) {
							userDetails = gUserDetails;
						} else {
							userDetails = zrUserDetails;
						}
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
