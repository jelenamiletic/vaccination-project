package com.xml.vakcinacija.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.PotvrdaNijePronadjenoException;
import com.xml.vakcinacija.exception.PotvrdaPostojiException;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.repository.PotvrdaRepository;
import com.xml.vakcinacija.service.PotvrdaService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class PotvrdaServiceImpl implements PotvrdaService{

	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;

	@Override
	public void dodajNoviPotvrda(String PotvrdaXML) throws Exception {
		Potvrda validanObjekat = (Potvrda) unmarshallerService.unmarshal(PotvrdaXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, XSDPutanjeKonstante.XSD_POTVRDA);
		if (validanObjekat != null) {
			String pronadjenPotvrdaXml = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG());
			if (pronadjenPotvrdaXml != null) {
				throw new PotvrdaPostojiException(validanObjekat.getLicneInformacije().getJMBG());
			}
			potvrdaRepository.savePotvrdaObjekat(validanObjekat);
		}
	}

	@Override
	public List<Potvrda> pronadjiSve() throws Exception {
		return potvrdaRepository.pronadjiSve();
	}

	@Override
	public Potvrda pronadjiPotvrdaPoJmbg(String jmbg) throws Exception {
		Potvrda potvrda = potvrdaRepository.pronadjiPotvrdaPoJmbg(jmbg);
		if (potvrda == null) {
			throw new PotvrdaNijePronadjenoException(jmbg);
		}
		return potvrda;
	}
}
