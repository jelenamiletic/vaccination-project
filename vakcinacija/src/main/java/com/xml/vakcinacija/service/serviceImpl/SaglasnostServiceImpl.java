package com.xml.vakcinacija.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.model.zdravstveni_radnik.ZdravstveniRadnik;
import com.xml.vakcinacija.repository.SaglasnostRepository;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SaglasnostService;
import com.xml.vakcinacija.service.TerminService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import com.xml.vakcinacija.utils.XSLKonstante;

@Service
public class SaglasnostServiceImpl implements SaglasnostService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private SaglasnostRepository saglasnostRepository;
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private PDFTransformerService pdfTransformerService;
	
	@Autowired
	private HTMLTransformerService htmlTransformerService;

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
			
			ZdravstveniRadnik radnik = (ZdravstveniRadnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validanObjekat.getZdravstveniRadnikSaglasnost().getLicneInformacijeLekara().getPunoIme().setIme(radnik.getPunoIme().getIme());
			validanObjekat.getZdravstveniRadnikSaglasnost().getLicneInformacijeLekara().getPunoIme().setPrezime(radnik.getPunoIme().getPrezime());
			
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
	public Saglasnost pronadjiNajnovijuPunuSaglasnostPoJmbgIliBrPasosa(String id) throws Exception {
		List<Saglasnost> saglasnosti = saglasnostRepository.pronadjiSaglasnostPoJmbgIliBrPasosa(id);
		if(saglasnosti.size() == 0)
		{
			return null;
		}
		
		Saglasnost najnovija = null;
		for (Saglasnost saglasnost : saglasnosti) {
			if(saglasnost.getZdravstveniRadnikSaglasnost() != null) {
				najnovija = saglasnost;
				break;
			}
		}
		
		if(najnovija != null) {
			for (Saglasnost saglasnost : saglasnosti) {
				if(saglasnost.getDatumPodnosenja().toGregorianCalendar().compareTo(najnovija.getDatumPodnosenja().toGregorianCalendar()) > 0
						&& saglasnost.getZdravstveniRadnikSaglasnost() != null) {
					najnovija = saglasnost;
				}
			}
		}
		return najnovija;
	}
	
	@Override
	public ByteArrayInputStream nabaviMetaPodatkeJSONPoId(String id, int brojDoze) throws Exception {
		boolean jmbg = false;
		
		if(id.length() == 13)
		{
			jmbg = true;
		}
		
		String xml = saglasnostRepository.pronadjiPoJmbgIBrojDoze(id, brojDoze, jmbg);
		if(xml.equals("")  || xml == null) {
			return null;
		}
		Saglasnost saglasnost = (Saglasnost) unmarshallerService.unmarshal(xml,
        		ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST);
		String query = String.format("?s ?p ?o. FILTER (?s = <%s>)", saglasnost.getAbout());
		return rdfService.getMetadataJSON(query, "saglasnost_" + id, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
	
	@Override
	public ByteArrayInputStream nabaviMetaPodatkeRDFPoId(String id, int brojDoze) throws Exception {
		boolean jmbg = false;
		
		if(id.length() == 13)
		{
			jmbg = true;
		}
		
		String xml = saglasnostRepository.pronadjiPoJmbgIBrojDoze(id, brojDoze, jmbg);
		if(xml.equals("")  || xml == null) {
			return null;
		}
		return rdfService.getMetadataRDF(xml);
	}

	@Override
	public ByteArrayInputStream generisiXhtml(String id, int brojDoze) throws Exception {
		boolean jmbg = false;
		
		if(id.length() == 13)
		{
			jmbg = true;
		}
		
		String xml = saglasnostRepository.pronadjiPoJmbgIBrojDoze(id, brojDoze, jmbg);
		if(xml.equals("")  || xml == null) {
			return null;
		}
		
		return htmlTransformerService.generateHTML(xml, XSLKonstante.SAGLASNOST_XSL);
	}

	@Override
	public ByteArrayInputStream generisiPdf(String id, int brojDoze) throws Exception {
		boolean jmbg = false;
		
		if(id.length() == 13)
		{
			jmbg = true;
		}
		
		List<Saglasnost> saglasnost = saglasnostRepository.pronadjiSaglasnostXmlPoFullId(id, jmbg);
		if (saglasnost == null) {
			throw new Exception();
		}
		
		String xml = saglasnostRepository.pronadjiPoJmbgIBrojDoze(id, brojDoze, jmbg);
		if(xml.equals("")  || xml == null) {
			return null;
		}
		
		return pdfTransformerService.generatePDF(xml, com.xml.vakcinacija.utils.XSLFOKonstante.SAGLASNOST_XSL_FO);
	}

	@Override
	public String pronadjiSveOsnovnaPretraga(String pretraga) throws Exception {
		String rez = "<Saglasnosti>";
		
		List<String> interesovanja = saglasnostRepository.pronadjiSveOsnovnaPretraga(pretraga);
		if(interesovanja != null && interesovanja.size() > 0) {
			for (String interesovanje : saglasnostRepository.pronadjiSveOsnovnaPretraga(pretraga)) {
				rez += interesovanje;
			}
		}
		
		rez+= "</Saglasnosti>";
		
		return rez;
	}

	@Override
	public String pronadjiSveNaprednaPretraga(String ime, String prezime, String jmbg, String pol) throws Exception {
		
		String rezultat = "<Saglasnosti>";
		
		List<Saglasnost> interesovanja = saglasnostRepository.pronadjiSve();
		if(interesovanja != null && interesovanja.size() > 0) {
			for (Saglasnost saglasnost : saglasnostRepository.pronadjiSve()) {
				
				if(ime.equals("NEMA") || ime == null || saglasnost.getPacijentSaglasnost().getLicneInformacije().getPunoIme().getIme().equals(ime)) {
					
					if(prezime.equals("NEMA") || prezime == null ||saglasnost.getPacijentSaglasnost().getLicneInformacije().getPunoIme().getIme().equals(ime)) {
						
						if(jmbg.equals("NEMA") || jmbg == null || ( saglasnost.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getRepublikaSrbija() != null && 
								saglasnost.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getRepublikaSrbija().getJMBG().getValue().equals(jmbg))
							|| (saglasnost.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getStranoDrzavljanstvo() != null &&
									saglasnost.getPacijentSaglasnost().getLicneInformacije().getDrzavljanstvo().getStranoDrzavljanstvo().getBrojPasosa().getValue().equals(jmbg))) {
							
							if(pol.equals("NEMA") || pol == null || saglasnost.getPacijentSaglasnost().getLicneInformacije().getPol().getValue().toString().equals(pol)) {
								
								rezultat += marshallerService.marshall(saglasnost, ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST);
							}
						}
					}
				}
			}
		}
		
		return rezultat + "</Saglasnosti>";
	}
}
