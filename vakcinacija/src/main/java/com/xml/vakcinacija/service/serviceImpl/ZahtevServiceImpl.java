package com.xml.vakcinacija.service.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.ZahtevNijePronadjenoException;
import com.xml.vakcinacija.exception.ZahtevPostojiException;
import com.xml.vakcinacija.model.OdgovorNaZahtev;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;
import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.repository.KorisnikRepository;
import com.xml.vakcinacija.repository.SertifikatRepository;
import com.xml.vakcinacija.repository.ZahtevRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SertifikatService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.service.ZahtevService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Service
public class ZahtevServiceImpl implements ZahtevService{
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private SertifikatRepository sertifikatRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private SertifikatService sertifikatService;
	
	@Autowired
	private Environment env;

	@Override
	public void dodajNoviZahtev(String zahtevXML) throws Exception {
		Zahtev validanObjekat = (Zahtev) unmarshallerService.unmarshal(zahtevXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_ZAHTEV, XSDPutanjeKonstante.XSD_ZAHTEV);
		if (validanObjekat != null) {
			String pronadjenZahtevXml = zahtevRepository.pronadjiZahtevXmlPoJmbg(validanObjekat.getPodnosilac().getJMBG().getValue());
			if (pronadjenZahtevXml != null) {
				throw new ZahtevPostojiException(validanObjekat.getPodnosilac().getJMBG().getValue());
			}
			validanObjekat.setDatumPodnosenja();
			zahtevRepository.saveZahtevObjekat(validanObjekat);
			
			try {
				rdfService.save(zahtevXML, "zahtev_" + validanObjekat.getPodnosilac().getJMBG().getValue(), 
						NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	@Override
	public List<Zahtev> dobaviSveNeodobreneZahteve() throws Exception {
		return zahtevRepository.pronadjiNeodobreneZahteve();
	}

	@Override
	public void promeniStatusZahteva(String jmbg, OdgovorNaZahtev odgovorNaZahtev) throws Exception {
		Gradjanin gradjanin = korisnikRepository.pronadjiGradjanina(null, jmbg);
		if (odgovorNaZahtev.getRazlogOdbijanja() == null) {
			Zahtev zahtev = zahtevRepository.pronadjiZahtevPoJmbg(jmbg);
			if (zahtev == null) {
				throw new ZahtevNijePronadjenoException(jmbg);
			}
			zahtev.setOdobren(true);
			zahtevRepository.saveZahtevObjekat(zahtev);
			Sertifikat sertifikat = sertifikatRepository.pronadjiSertifikatPoJmbg(jmbg);
			
			MimeMessage message = emailSenderService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(gradjanin.getEmail());
			helper.setSubject("Odgovor na zahtev");
			helper.setFrom(env.getProperty("spring.mail.username"));
			helper.setText("Postovani " + gradjanin.getPunoIme().getIme() + ",\n"
	        		+ "\tVas zahtev za digitalni zeleni serfifikat je odobren. "
	        		+ "\tU prilogu nalazi se sertifikat u PDF i XHTML formatu.");
			
			helper.addAttachment("Sertifikat.pdf", 
					new ByteArrayResource(
							sertifikatService.generisiPdf(sertifikat.getLicneInformacije().getJMBG().getValue()).readAllBytes()));
			helper.addAttachment("Sertifikat.htm", 
					new ByteArrayResource(
							sertifikatService.generisiXHTML(sertifikat.getLicneInformacije().getJMBG().getValue()).readAllBytes()));
	        emailSenderService.sendEmail(message);
		} else {
			zahtevRepository.izbrisiZahtev(jmbg);
			MimeMessage message = emailSenderService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(gradjanin.getEmail());
			helper.setSubject("Odgovor na zahtev");
			helper.setFrom(env.getProperty("spring.mail.username"));
			helper.setText(odgovorNaZahtev.getRazlogOdbijanja());
			emailSenderService.sendEmail(message);
		}
	}

	@Override
	public void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException {
		String query = String.format("?s ?p ?o. ?s <http://www.ftn.uns.ac.rs/rdf/zahtev/predicate/jmbg> \"%s\"^^<http://www.w3.org/2001/XMLSchemastring>", jmbg);
		rdfService.getMetadataXML(query, "zahtev_" + jmbg, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
}
