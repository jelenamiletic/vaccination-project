package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.PotvrdaNijePronadjenoException;
import com.xml.vakcinacija.exception.PotvrdaPostojiException;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.repository.PotvrdaRepository;
import com.xml.vakcinacija.service.PotvrdaService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class PotvrdaServiceImpl implements PotvrdaService{

	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;

	@Override
	public void dodajNoviPotvrda(String PotvrdaXML) throws Exception {
		Potvrda validanObjekat = (Potvrda) unmarshallerService.unmarshal(PotvrdaXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, XSDPutanjeKonstante.XSD_POTVRDA);
		if (validanObjekat != null) {
			int brojDoze = validanObjekat.getInformacijeOVakcinama().get(validanObjekat.getInformacijeOVakcinama().size() - 1).getBrojDoze();
			String pronadjenPotvrdaXml = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG().getValue(), brojDoze);
			if (pronadjenPotvrdaXml != null) {
				throw new PotvrdaPostojiException(validanObjekat.getLicneInformacije().getJMBG().getValue());
			}
			potvrdaRepository.savePotvrdaObjekat(validanObjekat);
			
			try {
				rdfService.save(PotvrdaXML, "potvrda_" + validanObjekat.getLicneInformacije().getJMBG().getValue()
						+ "_" + brojDoze, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Potvrda> pronadjiSve() throws Exception {
		return potvrdaRepository.pronadjiSve();
	}

	@Override
	public Potvrda pronadjiPotvrdaPoJmbg(String jmbg, int brojDoze) throws Exception {
		Potvrda potvrda = potvrdaRepository.pronadjiPotvrdaPoJmbg(jmbg, brojDoze);
		if (potvrda == null) {
			throw new PotvrdaNijePronadjenoException(jmbg);
		}
		return potvrda;
	}
	
	@Override
	public void nabaviMetaPodatkeXmlPoJmbg(String jmbg, int brojDoze) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/potvrda/%s_%d>)", jmbg, brojDoze);
		rdfService.getMetadataXML(query, "potvrda_" + jmbg + "_" + brojDoze, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
}
