package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.repository.SaglasnostRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SaglasnostService;
import com.xml.vakcinacija.service.TerminService;
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
	
	@Autowired
	private TerminService terminService;

	@Override
	public void dodajNovuSaglasnost(String XML) throws Exception {
		Saglasnost validanObjekat = (Saglasnost) unmarshallerService.unmarshal(XML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST);
		if (validanObjekat != null) {
			Gradjanin gradjanin = (Gradjanin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validanObjekat.getPacijentSaglasnost().getLicneInformacije().getPunoIme().setIme(gradjanin.getPunoIme().getIme());
			validanObjekat.getPacijentSaglasnost().getLicneInformacije().getPunoIme().setPrezime(gradjanin.getPunoIme().getPrezime());
			validanObjekat.getPacijentSaglasnost().getLicneInformacije().getPol().setValue(gradjanin.getPol());
			int indx = saglasnostRepository.saveSaglasnostObjekat(validanObjekat);
			
			terminService.postaviPopunjenaSaglasnost(gradjanin.getJMBG(), 1);
			
			try {
				rdfService.save(XML, "saglasnost_" + 
						validanObjekat.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo() + '_' + Integer.toString(indx), 
						NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void izmeniSaglasnost(String XML) throws Exception {
		Saglasnost validanObjekat = (Saglasnost) unmarshallerService.unmarshal(XML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST);
		if (validanObjekat != null) {
			saglasnostRepository.editSaglasnostObjekat(validanObjekat);			
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
	public Saglasnost pronadjiNajnovijuSaglasnostPoJmbgIliBrPasosa(String id) throws Exception {
		List<Saglasnost> saglasnosti = saglasnostRepository.pronadjiSaglasnostPoJmbgIliBrPasosa(id);
		if(saglasnosti.size() == 0)
		{
			return null;
		}
		
		Saglasnost najnovija = saglasnosti.get(0);
		for (Saglasnost saglasnost : saglasnosti) {
			if(saglasnost.getDatumPodnosenja().toGregorianCalendar().compareTo(najnovija.getDatumPodnosenja().toGregorianCalendar()) > 0) {
				najnovija = saglasnost;
			}
		}
		return najnovija;
	}
	
	@Override
	public void nabaviMetaPodatkeXmlPoId(String id) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/saglasnost/%s>)", id);
		rdfService.getMetadataXML(query, "saglasnost_" + id, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
}
