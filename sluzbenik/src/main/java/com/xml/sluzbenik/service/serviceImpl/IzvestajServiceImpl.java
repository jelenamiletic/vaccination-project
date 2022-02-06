package com.xml.sluzbenik.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xml.sluzbenik.exception.IzvestajNijePronadjenException;
import com.xml.sluzbenik.exception.IzvestajPostojiException;
import com.xml.sluzbenik.exception.NevalidanRedosledDatumaException;
import com.xml.sluzbenik.model.interesovanje.ListaInteresovanja;
import com.xml.sluzbenik.model.izvestaj.Izvestaj;
import com.xml.sluzbenik.repository.IzvestajRepository;
import com.xml.sluzbenik.security.TokenBasedAuthentication;
import com.xml.sluzbenik.service.IzvestajService;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.RDFService;
import com.xml.sluzbenik.service.UnmarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.NamedGraphURIKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@Service
public class IzvestajServiceImpl implements IzvestajService {
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private IzvestajRepository izvestajRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void dodajNoviIzvestaj(String izvestajXML) throws Exception {
		Izvestaj validanObjekat = (Izvestaj) unmarshallerService.unmarshal(izvestajXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, XSDPutanjeKonstante.XSD_IZVESTAJ);
		if (validanObjekat != null) {
			String odDatumStr = validanObjekat.getPeriodIzvestaja().getOdDatum().toString();
			String doDatumStr = validanObjekat.getPeriodIzvestaja().getDoDatum().toString();
			String odDoDatumiStr = odDatumStr + "_" + doDatumStr;
			XMLGregorianCalendar odDatum = validanObjekat.getPeriodIzvestaja().getOdDatum();
			XMLGregorianCalendar doDatum = validanObjekat.getPeriodIzvestaja().getDoDatum();
			if (odDatum.toGregorianCalendar().compareTo(doDatum.toGregorianCalendar()) > 0) {
				throw new NevalidanRedosledDatumaException(odDatumStr, doDatumStr);
			}
			String pronadjenIzvestajXml = izvestajRepository.pronadjiIzvestajXml(odDatumStr, doDatumStr);
			if (pronadjenIzvestajXml != null) {
				throw new IzvestajPostojiException(odDatumStr, doDatumStr);
			}
			izvestajRepository.saveIzvestajObjekat(validanObjekat);
			
			try {
				rdfService.save(izvestajXML, "izvestaj_" + odDoDatumiStr, 
						NamedGraphURIKonstante.SLUZBENIK_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Izvestaj> pronadjiSve() throws Exception {
//		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
//		HttpHeaders headers = new HttpHeaders();
//		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
//		headers.setBearerAuth(a.getToken());
//		headers.set("Sluzbenik", userDetails);
//		ResponseEntity<ListaInteresovanja> response = restTemplate.exchange(
//                "http://localhost:8080/interesovanje/pronadjiSve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaInteresovanja.class);
		return izvestajRepository.pronadjiSve();
	}

	@Override
	public Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception {
		Izvestaj izvestaj = izvestajRepository.pronadjiIzvestaj(odDatum, doDatum);
		if (izvestaj == null) {
			throw new IzvestajNijePronadjenException(odDatum, doDatum);
		}
		return izvestaj;
	}

	@Override
	public void nabaviMetaPodatkeXmlPoDatumima(String odDatum, String doDatum) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/izvestaj/%1$s/%2$s>)", odDatum, doDatum);
		rdfService.getMetadataXML(query, "izvestaj_" + odDatum + "_" + doDatum, NamedGraphURIKonstante.SLUZBENIK_NAMED_GRAPH);
	}

}