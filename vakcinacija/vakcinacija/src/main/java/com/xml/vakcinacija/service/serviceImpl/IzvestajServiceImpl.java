package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.IzvestajNijePronadjenException;
import com.xml.vakcinacija.exception.IzvestajPostojiException;
import com.xml.vakcinacija.exception.NevalidanRedosledDatumaException;
import com.xml.vakcinacija.model.izvestaj.Izvestaj;
import com.xml.vakcinacija.repository.IzvestajRepository;
import com.xml.vakcinacija.service.IzvestajService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class IzvestajServiceImpl implements IzvestajService {
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private IzvestajRepository izvestajRepository;

	@Override
	public void dodajNoviIzvestaj(String izvestajXML) throws Exception {
		Izvestaj validanObjekat = (Izvestaj) unmarshallerService.unmarshal(izvestajXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, XSDPutanjeKonstante.XSD_IZVESTAJ);
		if (validanObjekat != null) {
			String odDatumStr = validanObjekat.getPeriodIzvestaja().getOdDatum().toString();
			String doDatumStr = validanObjekat.getPeriodIzvestaja().getDoDatum().toString();
			String odDoDatumiStr = odDatumStr + "_" + doDatumStr;
			XMLGregorianCalendar odDatum = validanObjekat.getPeriodIzvestaja().getOdDatum();
			XMLGregorianCalendar doDatum = validanObjekat.getPeriodIzvestaja().getDoDatum();
			if (odDatum.toGregorianCalendar().compareTo(doDatum.toGregorianCalendar()) > 0) {
				throw new NevalidanRedosledDatumaException(odDatumStr, doDatumStr);
			}
			String pronadjenIzvestajXml = izvestajRepository.pronadjiIzvestajXml(odDatumStr, doDatumStr);
			if (pronadjenIzvestajXml != null) {
				throw new IzvestajPostojiException(odDatumStr, doDatumStr);
			}
			izvestajRepository.saveIzvestajObjekat(validanObjekat);
			
			try {
				rdfService.save(izvestajXML, "izvestaj_" + odDoDatumiStr, 
						NamedGraphURIKonstante.IZVESTAJ_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Izvestaj> pronadjiSve() throws Exception {
		return izvestajRepository.pronadjiSve();
	}

	@Override
	public Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception {
		Izvestaj izvestaj = izvestajRepository.pronadjiIzvestaj(odDatum, doDatum);
		if (izvestaj == null) {
			throw new IzvestajNijePronadjenException(odDatum, doDatum);
		}
		return izvestaj;
	}

	@Override
	public void nabaviMetaPodatkeXmlPoDatumima(String odDatum, String doDatum) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/izvestaj/%1$s/%2$s>)", odDatum, doDatum);
		rdfService.getMetadataXML(query, "izvestaj_" + odDatum + "_" + doDatum, NamedGraphURIKonstante.IZVESTAJ_NAMED_GRAPH);
	}

}
