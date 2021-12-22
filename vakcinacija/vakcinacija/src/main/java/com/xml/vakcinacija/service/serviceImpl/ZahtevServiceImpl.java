package com.xml.vakcinacija.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.ZahtevNijePronadjenoException;
import com.xml.vakcinacija.exception.ZahtevPostojiException;
import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.repository.ZahtevRepository;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.service.ZahtevService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class ZahtevServiceImpl implements ZahtevService{
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private ZahtevRepository zahtevRepository;

	@Override
	public void dodajNoviZahtev(String zahtevXML) throws Exception {
		Zahtev validanObjekat = (Zahtev) unmarshallerService.unmarshal(zahtevXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_ZAHTEV, XSDPutanjeKonstante.XSD_ZAHTEV);
		if (validanObjekat != null) {
			String pronadjenZahtevXml = zahtevRepository.pronadjiZahtevXmlPoJmbg(validanObjekat.getPodnosilac().getJMBG());
			if (pronadjenZahtevXml != null) {
				throw new ZahtevPostojiException(validanObjekat.getPodnosilac().getJMBG());
			}
			zahtevRepository.saveZahtevObjekat(validanObjekat);
		}
	}

	@Override
	public List<Zahtev> pronadjiSve() throws Exception {
		return zahtevRepository.pronadjiSve();
	}

	@Override
	public Zahtev pronadjiZahtevPoJmbg(String jmbg) throws Exception {
		Zahtev zahtev = zahtevRepository.pronadjiZahtevPoJmbg(jmbg);
		if (zahtev == null) {
			throw new ZahtevNijePronadjenoException(jmbg);
		}
		return zahtev;
	}

}
