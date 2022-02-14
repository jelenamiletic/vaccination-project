package com.xml.vakcinacija.service.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.repository.TerminRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.TerminService;
import com.xml.vakcinacija.service.UnmarshallerService;

@Service
public class TerminServiceImpl implements TerminService{

	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private TerminRepository terminRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Termin dodajNoviTermin(String jmbg, int brojDoze, String vakcina) throws Exception {
//		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
//		HttpHeaders headers = new HttpHeaders();
//		
//		headers.setBearerAuth(a.getToken());
//		headers.set("Gradjanin", userDetails);
//		
//		ResponseEntity<Boolean> response = restTemplate.exchange(
//                "http://localhost:8081/vakcina/proveriSmanjiKolicinu/" + vakcina, HttpMethod.PUT, new HttpEntity<Object>(), Boolean.class);
		
		List<Termin> termini = getSveTermine().stream()
                .sorted((a1, a2) -> {
                    XMLGregorianCalendar c1 = a1.getDatum();
                    XMLGregorianCalendar c2 = a2.getDatum();
                    return c1.toGregorianCalendar().compareTo(c2.toGregorianCalendar());
                })
                .collect(Collectors.toList());
		
		GregorianCalendar datum = new GregorianCalendar();
		GregorianCalendar newest = new GregorianCalendar();
		
		if(termini.size() != 0) {
			newest = termini.get(termini.size() - 1).getDatum().toGregorianCalendar();
		} 
		
		if(brojDoze != 1)  {
			datum.add(Calendar.MONTH, 6);
		} else {
			datum.add(Calendar.HOUR, 72);
		}
		
		if(datum.compareTo(newest) < 0) {
			datum = newest;
		}
		datum.add(Calendar.MINUTE, Termin.DUZINA_VAKCINACIJE);
		
		Termin termin = new Termin(DatatypeFactory.newInstance().newXMLGregorianCalendar(datum), jmbg, brojDoze, vakcina);
		terminRepository.saveTerminObjekat(termin);
		
		//TODO umanji vakcinu za 1
		
		return termin;
	}

	@Override
	public List<Termin> getTerminePoJmbg(String jmbg) throws Exception {
		List<Termin> termini = terminRepository.pronadjiSvePoJmbg(jmbg);
		if(termini == null) {
			termini = new ArrayList<Termin>();
		}
		return termini;
	}

	@Override
	public Termin getAktuelniTerminPoJmbg(String jmbg) throws Exception {
		Termin termin = null;
		int najvecaDoza = 0;
		
		for (Termin t : getTerminePoJmbg(jmbg)) {
			
			if(t.getBrojDoze() > najvecaDoza) {
				najvecaDoza = t.getBrojDoze();
				termin = t;
			}
			
		}
		return termin;
	}

	@Override
	public Termin getTerminPoJmbgIDozi(String jmbg, int brojDoze) throws Exception {
		return terminRepository.pronadjiTerminPoJmbgIDozi(jmbg, brojDoze);
	}

	@Override
	public List<Termin> getSveTermine() throws Exception {
		List<Termin> termini = terminRepository.pronadjiSve();
		if(termini == null) {
			termini = new ArrayList<Termin>();
		}
		return termini;
	}

	@Override
	public void postaviPopunjenaSaglasnost(String jmbg, int brojDoze) throws Exception {
		Termin termin = getTerminPoJmbgIDozi(jmbg, brojDoze);
		if(termin == null)
			return;
		termin.setPopunjenaSaglasnost(true);
		terminRepository.saveTerminObjekat(termin);
	}

	@Override
	public void postaviIzvrseno(String jmbg, int brojDoze) throws Exception {
		Termin termin = getTerminPoJmbgIDozi(jmbg, brojDoze);
		if(termin == null)
			return;
		termin.setIzvrseno(true);
		terminRepository.saveTerminObjekat(termin);
		
	}
	
	
}
