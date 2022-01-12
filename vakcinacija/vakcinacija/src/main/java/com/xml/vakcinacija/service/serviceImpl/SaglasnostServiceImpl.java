package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.repository.SaglasnostRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SaglasnostService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class SaglasnostServiceImpl implements SaglasnostService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private SaglasnostRepository saglasnostRepository;

	@Override
	public void dodajNovuSaglasnost(String XML) throws Exception {
		Saglasnost validanObjekat = (Saglasnost) unmarshallerService.unmarshal(XML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST);
		if (validanObjekat != null) {
			int indx = saglasnostRepository.saveSaglasnostObjekat(validanObjekat);
			
			try {
				rdfService.save(XML, "saglasnost_" + 
						validanObjekat.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo() + '_' + Integer.toString(indx), 
						NamedGraphURIKonstante.SAGLASNOST_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public List<Saglasnost> pronadjiSve() throws Exception {
		return saglasnostRepository.pronadjiSve();
	}

	@Override
	public List<Saglasnost> pronadjiSaglasnostPoJmbgIliBrPasosa(String id) throws Exception {
		return saglasnostRepository.pronadjiSaglasnostPoJmbgIliBrPasosa(id);
	}
	
	@Override
	public void nabaviMetaPodatkeXmlPoId(String id) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/saglasnost/%s>)", id);
		rdfService.getMetadataXML(query, "saglasnost_" + id, NamedGraphURIKonstante.SAGLASNOST_NAMED_GRAPH);
	}
}
