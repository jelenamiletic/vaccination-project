package com.xml.vakcinacija.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.repository.InteresovanjeRepository;
import com.xml.vakcinacija.service.InteresovanjeService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private InteresovanjeRepository interesovanjeRepository;

	@Override
	public void dodajNovoInteresovanje(String interesovanjeXML) throws Exception {
		Interesovanje validanObjekat = (Interesovanje) unmarshallerService.unmarshal(interesovanjeXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, XSDPutanjeKonstante.XSD_INTERESOVANJE);
		if (validanObjekat != null) {
			String pronadjenoInteresovanjeXml = interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG());
			if (pronadjenoInteresovanjeXml != null) {
				System.out.println("Interesovanje sa ovim JMBG-om (" + validanObjekat.getLicneInformacije().getJMBG() + ") vec postoji!");
				return;
			}
			interesovanjeRepository.saveInteresovanjeObjekat(validanObjekat);
		}
	}
	
	@Override
	public List<Interesovanje> pronadjiSve() throws Exception {
		return interesovanjeRepository.pronadjiSve();
	}

	@Override
	public String pronadjiInteresovanjeXmlPoJmbg(String jmbg) throws Exception {
		return interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(jmbg);
	}

	@Override
	public Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception {
		return interesovanjeRepository.pronadjiInteresovanjePoJmbg(jmbg);
	}
}
