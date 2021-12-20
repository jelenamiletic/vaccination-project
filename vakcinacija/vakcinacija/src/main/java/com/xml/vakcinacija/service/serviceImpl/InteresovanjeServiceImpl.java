package com.xml.vakcinacija.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.model.Interesovanje;
import com.xml.vakcinacija.repository.InteresovanjeRepository;
import com.xml.vakcinacija.service.InteresovanjeService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private InteresovanjeRepository interesovanjeRepository;

	@Override
	public void dodajNovoInteresovanje(String interesovanjeXML) throws Exception {
		Interesovanje validanObjekat = (Interesovanje) unmarshallerService.unmarshal(interesovanjeXML, XSDPutanjeKonstante.XSD_INTERESOVANJE);
		if (validanObjekat != null) {
			Interesovanje pronadjenoInteresovanje = this.pronadjiInteresovanjePoJmbg(validanObjekat.getLicneInformacije().getJMBG());
			
			if (pronadjenoInteresovanje != null && pronadjenoInteresovanje.getLicneInformacije().getJMBG()
					.equals(validanObjekat.getLicneInformacije().getJMBG())) {
				System.out.println("Interesovanje sa ovim JMBG-om (" + validanObjekat.getLicneInformacije().getJMBG() + ") vec postoji!");
				return;
			}
			interesovanjeRepository.save(interesovanjeXML, validanObjekat.getLicneInformacije().getJMBG());
		}
	}

	@Override
	public Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception {
		String xml = interesovanjeRepository.pronadjiInteresovanjePoJMBG(jmbg);
		if (xml != null) {
			return (Interesovanje) unmarshallerService.unmarshal(xml, XSDPutanjeKonstante.XSD_INTERESOVANJE);
		}
		return null;
	}
}
