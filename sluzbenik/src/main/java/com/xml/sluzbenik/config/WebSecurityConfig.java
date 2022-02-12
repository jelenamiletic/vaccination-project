package com.xml.sluzbenik.config;

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

import com.xml.sluzbenik.security.RestAuthenticationEntryPoint;
import com.xml.sluzbenik.security.TokenAuthenticationFilter;
import com.xml.sluzbenik.security.TokenUtils;
import com.xml.sluzbenik.service.serviceImpl.CustomUserDetailsService;
import com.xml.sluzbenik.utils.RoleKonstante;

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
				.antMatchers
				(
						"/izvestaj/dodajNoviIzvestaj", 
						"/izvestaj/pronadjiSve", 
						"/izvestaj/pronadjiSve", 
						"/izvestaj/pronadjiIzvestaj/{odDatum}/{doDatum}", 
						"/izvestaj/nabaviMetaPodatkeXmlPoDatumima/{odDatum}/{doDatum}",
						"/izvestaj/generisiPdf/{odDatum}/{doDatum}",
						"/izvestaj/generisiXHTML/{odDatum}/{doDatum}"
				).hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				
				.antMatchers("/vakcina/dobaviSve", "/vakcina/azurirajKolicinu").hasAuthority(RoleKonstante.ROLE_SLUZBENIK)
				.antMatchers("/smanjiKolicinu/{nazivVakcine}").hasAuthority(RoleKonstante.ROLE_ZDRAVSTVENI_RADNIK)
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
