package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.xml.vakcinacija.exception.InteresovanjeNijePronadjenoException;
import com.xml.vakcinacija.exception.InteresovanjePostojiException;
import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.repository.InteresovanjeRepository;
import com.xml.vakcinacija.service.InteresovanjeService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private InteresovanjeRepository interesovanjeRepository;

	@Override
	public void dodajNovoInteresovanje(String interesovanjeXML) throws Exception {
		Interesovanje validanObjekat = (Interesovanje) unmarshallerService.unmarshal(interesovanjeXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, XSDPutanjeKonstante.XSD_INTERESOVANJE);
		if (validanObjekat != null) {
			String pronadjenoInteresovanjeXml = interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG());
			if (pronadjenoInteresovanjeXml != null) {
				throw new InteresovanjePostojiException(validanObjekat.getLicneInformacije().getJMBG());
			}
			interesovanjeRepository.saveInteresovanjeObjekat(validanObjekat);
		}
	}
	
	@Override
	public List<Interesovanje> pronadjiSve() throws Exception {
		return interesovanjeRepository.pronadjiSve();
	}

	@Override
	public Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception {
		Interesovanje interesovanje = interesovanjeRepository.pronadjiInteresovanjePoJmbg(jmbg);
		if (interesovanje == null) {
			throw new InteresovanjeNijePronadjenoException(jmbg);
		}
		return interesovanje;
	}
	
	@Override
	public void upisiMetapodatke(String xml) throws SAXException {
		Interesovanje validanObjekat = (Interesovanje) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, null);
		try {
			rdfService.save(xml, "interesovanje_" + validanObjekat.getLicneInformacije().getJMBG(), 
					NamedGraphURIKonstante.INTERESOVANJE_NAMED_GRAPH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException {
		String query = String.format("?s ?p ?o. ?s <http://www.ftn.uns.ac.rs/rdf/interesovanje/predicate/jmbg> \"%s\"^^<http://www.w3.org/2001/XMLSchemastring>", jmbg);
		rdfService.getMetadataXML(query, "interesovanje_" + jmbg, NamedGraphURIKonstante.INTERESOVANJE_NAMED_GRAPH);
	}
}
