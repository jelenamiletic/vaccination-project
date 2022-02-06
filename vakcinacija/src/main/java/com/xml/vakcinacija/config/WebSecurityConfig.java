package com.xml.vakcinacija.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.xml.vakcinacija.security.RestAuthenticationEntryPoint;
import com.xml.vakcinacija.security.TokenAuthenticationFilter;
import com.xml.vakcinacija.security.TokenUtils;
import com.xml.vakcinacija.service.serviceImpl.CustomUserDetailsService;
import com.xml.vakcinacija.utils.RoleKonstante;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
			.authorizeRequests().antMatchers("/auth/login").permitAll()
			
				// Interesovanje controller
				.antMatchers("/interesovanje/dodajNovoInteresovanje").hasAuthority(RoleKonstante.ROLE_GRADJANIN)
				.antMatchers("/interesovanje/pronadjiSve").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/interesovanje/pronadjiInteresovanjePoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/interesovanje/nabaviMetaPodatkeXmlPoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				
				// Potvrda controller
				.antMatchers("/potvrda/dodajNovuPotvrdu").hasAuthority(RoleKonstante.ROLE_ZDRAVSTVENI_RADNIK)
				.antMatchers("/potvrda/pronadjiSve").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/potvrda/pronadjiPotvrdaPoJmbg/{jmbg}/{brojDoze}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/potvrda/nabaviMetaPodatkeXmlPoJmbg/{jmbg}/{brojDoze}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				
				// Saglasnost controller
				.antMatchers("/saglasnost/dodajNovuSaglasnost").hasAuthority(RoleKonstante.ROLE_GRADJANIN)
				.antMatchers("/saglasnost/pronadjiSve").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/saglasnost/pronadjiSaglasnostPoJmbgIliBrPasosa/{id}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/saglasnost/nabaviMetaPodatkeXmlPoId/{id}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				
				// Sertifikat controller
				.antMatchers("/sertifikat/dodajNoviSertifikat").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/sertifikat/pronadjiSve").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/sertifikat/pronadjiSertifikatPoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/sertifikat/nabaviMetaPodatkeXmlPoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				
				// Zahtev controller
				.antMatchers("/zahtev/dodajNoviZahtev").hasAuthority(RoleKonstante.ROLE_GRADJANIN)
				.antMatchers("/zahtev/pronadjiSve").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/zahtev/pronadjiZahtevPoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/zahtev/nabaviMetaPodatkeXmlPoJmbg/{jmbg}").hasAnyAuthority(RoleKonstante.ROLE_GRADJANIN, RoleKonstante.ROLE_SLUZBENIK)
				
				// Gradjanin controller
				.antMatchers("/gradjanin/registracija").permitAll()
			.anyRequest().authenticated().and()
			.cors().and()
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), BasicAuthenticationFilter.class);
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}

}