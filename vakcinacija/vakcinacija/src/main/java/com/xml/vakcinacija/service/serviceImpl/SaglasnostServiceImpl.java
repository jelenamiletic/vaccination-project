package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.SaglasnostNijePronadjenaException;
import com.xml.vakcinacija.exception.SaglasnostPostojiException;
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
			String pronadjenaSaglasnostXml = saglasnostRepository.pronadjiSaglasnostXmlPoJmbg(validanObjekat.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo(), false);
			if (pronadjenaSaglasnostXml != null) {
				throw new SaglasnostPostojiException(validanObjekat.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getRepublikaSrbija().getJMBG().getValue());
			}
			saglasnostRepository.saveSaglasnostObjekat(validanObjekat);
		
			
			try {
				rdfService.save(XML, "saglasnost_" + 
						validanObjekat.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getRepublikaSrbija().getJMBG().getValue(), 
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
	public Saglasnost pronadjiSaglasnostPoJmbg(String jmbg) throws Exception {
		Saglasnost saglasnost = saglasnostRepository.pronadjiSaglasnostPoJmbg(jmbg);
		if (saglasnost == null) {
			throw new SaglasnostNijePronadjenaException(jmbg);
		}
		return saglasnost;
	}
	
	@Override
	public void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException {
		String query = String.format("?s ?p ?o. ?s <http://www.ftn.uns.ac.rs/rdf/saglasnost/predicate/jmbg> \"%s\"^^<http://www.w3.org/2001/XMLSchemastring>", jmbg);
		rdfService.getMetadataXML(query, "saglasnost_" + jmbg, NamedGraphURIKonstante.SAGLASNOST_NAMED_GRAPH);
	}
}
