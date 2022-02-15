package com.xml.sluzbenik.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xml.sluzbenik.model.pretraga.PretragaDokumenata;
import com.xml.sluzbenik.security.TokenBasedAuthentication;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.PretragaService;
import com.xml.sluzbenik.service.UnmarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@Service
public class PretragaServiceImpl implements PretragaService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private MarshallerService marshallerService;

	@Override
	public String osnovnaPretraga(String pretragaDokumenata)  throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<String> saglasnosti = restTemplate.exchange(
                "http://localhost:8080/saglasnost/pronadjiSveOsnovnaPretraga/" + pretragaDokumenata, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		
		ResponseEntity<String> potvrde = restTemplate.exchange(
                "http://localhost:8080/potvrda/pronadjiSveOsnovnaPretraga/" + pretragaDokumenata, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		
		ResponseEntity<String> sertifikati = restTemplate.exchange(
                "http://localhost:8080/sertifikat/pronadjiSveOsnovnaPretraga/" + pretragaDokumenata, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		
		
		return  "<Dokumenti>" + saglasnosti.getBody() + potvrde.getBody() + sertifikati.getBody() + "</Dokumenti>";
	}

	@Override
	public String naprednaPretraga(String pretragaDokumenata) throws Exception {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		PretragaDokumenata pretraga = (PretragaDokumenata) unmarshallerService.unmarshal(pretragaDokumenata, ContextPutanjeKonstante.CONTEXT_PUTANJA_PRETRAGA, null);
		
		String rezultat = "";
		switch(pretraga.getTipDokumenta()) {
			case POTVRDA:
				ResponseEntity<String> potvrda = restTemplate.exchange(
		                "http://localhost:8080/potvrda/pronadjiSveNaprednaPretraga/" + pretraga.getIme() + "/"
		                + pretraga.getPrezime() + "/" + pretraga.getJMBG() + "/" + pretraga.getPol(), HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
				rezultat = potvrda.getBody();
				
				break;
			case SAGLASNOST:
				ResponseEntity<String> saglasnosti = restTemplate.exchange(
		                "http://localhost:8080/saglasnost/pronadjiSveNaprednaPretraga/" + pretraga.getIme() + "/"
		                + pretraga.getPrezime() + "/" + pretraga.getJMBG() + "/" + pretraga.getPol(), HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
				rezultat = saglasnosti.getBody();
				
				break;
			case SERTIFIKAT:
				ResponseEntity<String> sertifikati = restTemplate.exchange(
		                "http://localhost:8080/sertifikat/pronadjiSveNaprednaPretraga/" + pretraga.getIme() + "/"
		                + pretraga.getPrezime() + "/" + pretraga.getJMBG() + "/" + pretraga.getPol(), HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
				rezultat = sertifikati.getBody();
				
				break;
			default:
				rezultat = "glup si";
				break;
		}
		
		return rezultat;
	}
	
}
