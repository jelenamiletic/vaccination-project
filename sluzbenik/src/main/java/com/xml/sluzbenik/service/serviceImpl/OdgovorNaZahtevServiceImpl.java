package com.xml.sluzbenik.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.xml.sluzbenik.model.OdgovorNaZahtev;
import com.xml.sluzbenik.model.zahtev.ListaZahteva;
import com.xml.sluzbenik.security.TokenBasedAuthentication;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.OdgovorNaZahtevService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

public class OdgovorNaZahtevServiceImpl implements OdgovorNaZahtevService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MarshallerService marshallerService;

	@Override
	public ListaZahteva dobaviSveNeodobreneZahteve() throws SAXException {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		ResponseEntity<ListaZahteva> response = restTemplate.exchange(
                "http://localhost:8080/zahtev/dobaviSveNeodobreneZahteve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaZahteva.class);
		
		return response.getBody();
	}

	@Override
	public void promeniStatusZahteva(String jmbg, OdgovorNaZahtev odgovorNaZahtev) throws SAXException {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		restTemplate.exchange("http://localhost:8080/zahtev/dobaviSveNeodobreneZahteve", HttpMethod.PUT, new HttpEntity<Object>(odgovorNaZahtev, headers), Void.class);
	}
}
