package com.xml.sluzbenik.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.xml.sluzbenik.exception.IzvestajNijePronadjenException;
import com.xml.sluzbenik.exception.NevalidanRedosledDatumaException;
import com.xml.sluzbenik.model.IzvestajBrojDoza;
import com.xml.sluzbenik.model.Period;
import com.xml.sluzbenik.model.Proizvodjac;
import com.xml.sluzbenik.model.interesovanje.Interesovanje;
import com.xml.sluzbenik.model.interesovanje.ListaInteresovanja;
import com.xml.sluzbenik.model.izvestaj.Izvestaj;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.BrojPodnetihDokumenata;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.KolicnaDozaPoRednomBroju;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.PeriodIzvestaja;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.RaspodelaDozaPoProizvodjacu;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.ZahteviZaDigitalniSertifikat;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.ZahteviZaDigitalniSertifikat.BrojIzdatih;
import com.xml.sluzbenik.model.izvestaj.Izvestaj.ZahteviZaDigitalniSertifikat.BrojPrimljenih;
import com.xml.sluzbenik.model.potvrda.ListaPotvrda;
import com.xml.sluzbenik.model.potvrda.Potvrda;
import com.xml.sluzbenik.model.potvrda.Potvrda.Vakcine;
import com.xml.sluzbenik.model.sertifikat.ListaSertifikata;
import com.xml.sluzbenik.model.sertifikat.Sertifikat;
import com.xml.sluzbenik.model.zahtev.ListaZahteva;
import com.xml.sluzbenik.model.zahtev.Zahtev;
import com.xml.sluzbenik.repository.IzvestajRepository;
import com.xml.sluzbenik.security.TokenBasedAuthentication;
import com.xml.sluzbenik.service.IzvestajService;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.RDFService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.NamedGraphURIKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;
import com.xml.sluzbenik.utils.XSLFOKonstante;

@Service
public class IzvestajServiceImpl implements IzvestajService {
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private IzvestajRepository izvestajRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PDFTransformerService pdfTransformerService;

	@Override
	public Izvestaj dodajNoviIzvestaj(Period period) throws Exception {
		String odDatumStr = period.getOdDatum().toString();
		String doDatumStr = period.getDoDatum().toString();
		String odDoDatumiStr = odDatumStr + "_" + doDatumStr;
		if (period.getOdDatum().toGregorianCalendar().compareTo(period.getDoDatum().toGregorianCalendar()) > 0) {
			throw new NevalidanRedosledDatumaException(odDatumStr, doDatumStr);
		}
		Izvestaj izvestaj = formirajIzvestaj(period);
		izvestaj.setVocab("http://www.ftn.uns.ac.rs/rdf/izvestaj/");
		izvestaj.setAbout("http://www.ftn.uns.ac.rs/rdf/izvestaj/" + odDatumStr + "/" + doDatumStr);
		String validanXml = marshallerService.marshall(izvestaj, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, XSDPutanjeKonstante.XSD_IZVESTAJ);
		if (validanXml != null) {
			izvestajRepository.saveIzvestajObjekat(izvestaj);
			
			try {
				rdfService.save(validanXml, "izvestaj_" + odDoDatumiStr, 
						NamedGraphURIKonstante.SLUZBENIK_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return izvestaj;
		}
		return null;
	}
	
	private Izvestaj formirajIzvestaj(Period period) throws SAXException, DatatypeConfigurationException {
		TokenBasedAuthentication a = (TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication();
		HttpHeaders headers = new HttpHeaders();
		String userDetails = marshallerService.marshall(a.getPrincipal(), ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK);
		headers.setBearerAuth(a.getToken());
		headers.set("Sluzbenik", userDetails);
		
		int brojInteresovanja = dobaviUkupanBrojInteresovanja(period, headers);
		int brojZahteva = dobaviUkupanBrojZahteva(period, headers);
		int brojSerfifikata = dobaviUkupanBrojSertifikata(period, headers);
		IzvestajBrojDoza ibd = dobaviBrojDoza(period, headers);
		
		Izvestaj izvestaj = new Izvestaj();
		
		PeriodIzvestaja pi = new PeriodIzvestaja();
		pi.setOdDatum(period.getOdDatum());
		pi.setDoDatum(period.getDoDatum());
		izvestaj.setPeriodIzvestaja(pi);
		
		BrojPodnetihDokumenata bpd = new BrojPodnetihDokumenata();
		bpd.setValue(String.valueOf(brojInteresovanja));
		bpd.setDatatype("xs:integer");
		bpd.setProperty("pred:brojPodnetihDokumenata");
		izvestaj.setBrojPodnetihDokumenata(bpd);
		
		ZahteviZaDigitalniSertifikat zzds = new ZahteviZaDigitalniSertifikat();
		BrojPrimljenih bp = new BrojPrimljenih();
		bp.setValue(String.valueOf(brojZahteva));
		bp.setDatatype("xs:integer");
		bp.setProperty("pred:brojPrimljenih");
		BrojIzdatih bi = new BrojIzdatih();
		bi.setValue(String.valueOf(brojSerfifikata));
		bi.setDatatype("xs:integer");
		bi.setProperty("pred:brojIzdatih");
		zzds.setBrojPrimljenih(bp);
		zzds.setBrojIzdatih(bi);
		izvestaj.setZahteviZaDigitalniSertifikat(zzds);
		
		izvestaj.setUkupanBrojDatihDoza(ibd.getUkupanBrojPrvihDoza() + ibd.getUkupanBrojDrugihDoza() 
			+ ibd.getUkupanBrojTrecihDoza());
		
		List<KolicnaDozaPoRednomBroju> listaKdprb = new ArrayList<KolicnaDozaPoRednomBroju>();
		KolicnaDozaPoRednomBroju kdprb1 = new KolicnaDozaPoRednomBroju();
		kdprb1.setRedniBroj(1);
		kdprb1.setBrojDatihDoza(ibd.getUkupanBrojPrvihDoza());
		KolicnaDozaPoRednomBroju kdprb2 = new KolicnaDozaPoRednomBroju();
		kdprb2.setRedniBroj(2);
		kdprb2.setBrojDatihDoza(ibd.getUkupanBrojDrugihDoza());
		KolicnaDozaPoRednomBroju kdprb3 = new KolicnaDozaPoRednomBroju();
		kdprb3.setRedniBroj(3);
		kdprb3.setBrojDatihDoza(ibd.getUkupanBrojTrecihDoza());
		listaKdprb.add(kdprb1);
		listaKdprb.add(kdprb2);
		listaKdprb.add(kdprb3);
		izvestaj.setKolicnaDozaPoRednomBroju(listaKdprb);
		
		List<RaspodelaDozaPoProizvodjacu> listaRdpp = new ArrayList<RaspodelaDozaPoProizvodjacu>();
		RaspodelaDozaPoProizvodjacu rdpp1 = new RaspodelaDozaPoProizvodjacu();
		rdpp1.setProizvodjac(Proizvodjac.PFIZER);
		rdpp1.setBrojDatihDoza(ibd.getUkupanBrojPfizerDoza());
		RaspodelaDozaPoProizvodjacu rdpp2 = new RaspodelaDozaPoProizvodjacu();
		rdpp2.setProizvodjac(Proizvodjac.MODERNA);
		rdpp2.setBrojDatihDoza(ibd.getUkupanBrojModernaDoza());
		RaspodelaDozaPoProizvodjacu rdpp3 = new RaspodelaDozaPoProizvodjacu();
		rdpp3.setProizvodjac(Proizvodjac.ASTRA_ZENECA);
		rdpp3.setBrojDatihDoza(ibd.getUkupanBrojAstraZenecaDoza());
		RaspodelaDozaPoProizvodjacu rdpp4 = new RaspodelaDozaPoProizvodjacu();
		rdpp4.setProizvodjac(Proizvodjac.SINOPHARM);
		rdpp4.setBrojDatihDoza(ibd.getUkupanBrojSinopharmDoza());
		RaspodelaDozaPoProizvodjacu rdpp5 = new RaspodelaDozaPoProizvodjacu();
		rdpp5.setProizvodjac(Proizvodjac.SPUTNIK);
		rdpp5.setBrojDatihDoza(ibd.getUkupanBrojSputnikDoza());
		listaRdpp.add(rdpp1);
		listaRdpp.add(rdpp2);
		listaRdpp.add(rdpp3);
		listaRdpp.add(rdpp4);
		listaRdpp.add(rdpp5);
		izvestaj.setRaspodelaDozaPoProizvodjacu(listaRdpp);
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar datumIzdavanja = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		izvestaj.setDatumIzdavanja(datumIzdavanja);
		return izvestaj;
	}
	
	private int dobaviUkupanBrojInteresovanja(Period period, HttpHeaders headers) {
		ResponseEntity<ListaInteresovanja> response = restTemplate.exchange(
                "http://localhost:8080/interesovanje/pronadjiSve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaInteresovanja.class);
		List<Interesovanje> interesovanja =  response.getBody().getInteresovanje();
		int brojInteresovanja = 0;
		for (Interesovanje i : interesovanja) {
			if (i.getDatumPodnosenja().toGregorianCalendar().compareTo(period.getOdDatum().toGregorianCalendar()) >= 0 && 
				i.getDatumPodnosenja().toGregorianCalendar().compareTo(period.getDoDatum().toGregorianCalendar()) <= 0) {
				brojInteresovanja++;
			}
		}
		return brojInteresovanja;
	}
	
	private int dobaviUkupanBrojZahteva(Period period, HttpHeaders headers) {
		ResponseEntity<ListaZahteva> response = restTemplate.exchange(
                "http://localhost:8080/zahtev/pronadjiSve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaZahteva.class);
		List<Zahtev> zahtevi =  response.getBody().getZahtev();
		int brojZahteva = 0;
		for (Zahtev z : zahtevi) {
			if (z.getDatumPodnosenja().toGregorianCalendar().compareTo(period.getOdDatum().toGregorianCalendar()) >= 0 && 
				z.getDatumPodnosenja().toGregorianCalendar().compareTo(period.getDoDatum().toGregorianCalendar()) <= 0) {
				brojZahteva++;
			}
		}
		return brojZahteva;
	}
	
	private int dobaviUkupanBrojSertifikata(Period period, HttpHeaders headers) {
		ResponseEntity<ListaSertifikata> response = restTemplate.exchange(
                "http://localhost:8080/sertifikat/pronadjiSve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaSertifikata.class);
		List<Sertifikat> sertifikati =  response.getBody().getSertifikat();
		int brojSerfifikata = 0;
		for (Sertifikat s : sertifikati) {
			if (s.getDatumVremeIzdavanja().toGregorianCalendar().compareTo(period.getOdDatum().toGregorianCalendar()) >= 0 && 
				s.getDatumVremeIzdavanja().toGregorianCalendar().compareTo(period.getDoDatum().toGregorianCalendar()) <= 0) {
				brojSerfifikata++;
			}
		}
		return brojSerfifikata;
	}
	
	private IzvestajBrojDoza dobaviBrojDoza(Period period, HttpHeaders headers) {
		ResponseEntity<ListaPotvrda> response = restTemplate.exchange(
                "http://localhost:8080/potvrda/pronadjiSve", HttpMethod.GET, new HttpEntity<Object>(headers), ListaPotvrda.class);
		List<Potvrda> potvrde =  response.getBody().getPotvrda();
		int doza1 = 0;
		int doza2 = 0;
		int doza3 = 0;
		int pfizer = 0;
		int sputnik = 0;
		int sinopharm = 0;
		int astraZeneca = 0;
		int moderna = 0;
		for (Potvrda p : potvrde) {
			if (p.getDatumIzdavanja().toGregorianCalendar().compareTo(period.getOdDatum().toGregorianCalendar()) >= 0 && 
					p.getDatumIzdavanja().toGregorianCalendar().compareTo(period.getDoDatum().toGregorianCalendar()) <= 0) {
				if (p.getInformacijeOVakcinama().size() == 1) {
					doza1++;
				} else if (p.getInformacijeOVakcinama().size() == 2) {
					doza2++;
				} else if (p.getInformacijeOVakcinama().size() == 3) {
					doza3++;
				}
				
				if (p.getInformacijeOVakcinama().size() == 1 || p.getInformacijeOVakcinama().size() == 2) {
					if (p.getVakcinaPrveDveDoze().getValue().equals(Proizvodjac.PFIZER)) {
						pfizer++;
					} else if (p.getVakcinaPrveDveDoze().getValue().equals(Proizvodjac.MODERNA)) {
						moderna++;
					} else if (p.getVakcinaPrveDveDoze().getValue().equals(Proizvodjac.ASTRA_ZENECA)) {
						astraZeneca++;
					} else if (p.getVakcinaPrveDveDoze().getValue().equals(Proizvodjac.SINOPHARM)) {
						sinopharm++;
					} else if (p.getVakcinaPrveDveDoze().getValue().equals(Proizvodjac.SPUTNIK)) {
						sputnik++;
					} 
				} else if (p.getInformacijeOVakcinama().size() == 3) {
					for (Vakcine v : p.getVakcine()) {
						if (v.getVakcina().getValue().equals(Proizvodjac.PFIZER)) {
							pfizer++;
						} else if (v.getVakcina().getValue().equals(Proizvodjac.MODERNA)) {
							moderna++;
						} else if (v.getVakcina().getValue().equals(Proizvodjac.ASTRA_ZENECA)) {
							astraZeneca++;
						} else if (v.getVakcina().getValue().equals(Proizvodjac.SINOPHARM)) {
							sinopharm++;
						} else if (v.getVakcina().getValue().equals(Proizvodjac.SPUTNIK)) {
							sputnik++;
						} 
					}
				}
			}
		}
		IzvestajBrojDoza ibd = new IzvestajBrojDoza();
		ibd.setUkupanBrojPrvihDoza(doza1);
		ibd.setUkupanBrojDrugihDoza(doza2);
		ibd.setUkupanBrojTrecihDoza(doza3);
		ibd.setUkupanBrojPfizerDoza(pfizer);
		ibd.setUkupanBrojAstraZenecaDoza(astraZeneca);
		ibd.setUkupanBrojModernaDoza(moderna);
		ibd.setUkupanBrojSputnikDoza(sputnik);
		ibd.setUkupanBrojSinopharmDoza(sinopharm);
		return ibd;
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
		rdfService.getMetadataXML(query, "izvestaj_" + odDatum + "_" + doDatum, NamedGraphURIKonstante.SLUZBENIK_NAMED_GRAPH);
	}

	@Override
	public ByteArrayInputStream generisiPdf(String odDatum, String doDatum) throws Exception {
		String izvestajXml = izvestajRepository.pronadjiIzvestajXml(odDatum, doDatum);
		if (izvestajXml == null) {
			throw new IzvestajNijePronadjenException(odDatum, doDatum);
		}
		return pdfTransformerService.generatePDF(izvestajXml, XSLFOKonstante.IZVESTAJ_XSL_FO);
	}
}
