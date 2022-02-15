package com.xml.vakcinacija.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xml.vakcinacija.exception.ZahtevNijePronadjenoException;
import com.xml.vakcinacija.exception.ZahtevPostojiException;
import com.xml.vakcinacija.model.OdgovorNaZahtev;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;

import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.repository.KorisnikRepository;
import com.xml.vakcinacija.repository.PotvrdaRepository;
import com.xml.vakcinacija.repository.SertifikatRepository;
import com.xml.vakcinacija.repository.ZahtevRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SertifikatService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.service.ZahtevService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import com.xml.vakcinacija.utils.XSLFOKonstante;
import com.xml.vakcinacija.utils.XSLKonstante;

@Service
public class ZahtevServiceImpl implements ZahtevService{
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private PDFTransformerService pdfTransformerService;
	
	@Autowired
	private HTMLTransformerService htmlTransformerService;

	private SertifikatRepository sertifikatRepository;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private SertifikatService sertifikatService;
	

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
			Gradjanin gradjanin = (Gradjanin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validanObjekat.getPodnosilac().getPunoIme().setIme(gradjanin.getPunoIme().getIme());
			validanObjekat.getPodnosilac().getPunoIme().setPrezime(gradjanin.getPunoIme().getPrezime());
			validanObjekat.getPodnosilac().setPol(gradjanin.getPol());
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
		List<Zahtev> neodobreniZahteviViseOdJedneDoze = new ArrayList<Zahtev>();
		List<Zahtev> neodobreniZahtevi = zahtevRepository.pronadjiNeodobreneZahteve();
		List<Potvrda> potvrde = potvrdaRepository.pronadjiSve();
		for (Zahtev z : neodobreniZahtevi) {
			for (Potvrda p : potvrde) {
				if (z.getPodnosilac().getJMBG().getValue().equals(p.getLicneInformacije().getJMBG().getValue()) && 
						p.getInformacijeOVakcinama().size() > 1) {
					neodobreniZahteviViseOdJedneDoze.add(z);
					break;
				}
			}
		}
		return neodobreniZahteviViseOdJedneDoze;
	}

	@Override
	public void promeniStatusZahteva(OdgovorNaZahtev odgovorNaZahtev) throws Exception {
		Gradjanin gradjanin = korisnikRepository.pronadjiGradjanina(null, odgovorNaZahtev.getJMBG());
		if (odgovorNaZahtev.getRazlogOdbijanja() == null) {
			Zahtev zahtev = zahtevRepository.pronadjiZahtevPoJmbg(odgovorNaZahtev.getJMBG());
			if (zahtev == null) {
				throw new ZahtevNijePronadjenoException(odgovorNaZahtev.getJMBG());
			}
			zahtev.setOdobren(true);
			zahtevRepository.saveZahtevObjekat(zahtev);
			Sertifikat sertifikat = sertifikatRepository.pronadjiSertifikatPoJmbg(odgovorNaZahtev.getJMBG());
			
			MimeMessage message = emailSenderService.createMimeMessage();
			InternetAddress sender = new InternetAddress("mrs_isa_2021_t15_5@hotmail.com");
	        InternetAddress recipient = new InternetAddress(gradjanin.getEmail());
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Odgovor na zahtev");
			message.setSender(sender);
			
		    MimeMultipart mimeMultipart = new MimeMultipart();
		    
		    MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText("Postovani " + gradjanin.getPunoIme().getIme() + ",\n"
	        		+ "\tVas zahtev za digitalni zeleni serfifikat je odobren. "
	        		+ "U prilogu nalazi se sertifikat u PDF i XHTML formatu.");
	        mimeMultipart.addBodyPart(textBodyPart);
	        
			MimeBodyPart attachment = new MimeBodyPart();
		    ByteArrayDataSource ds = new ByteArrayDataSource(
		    		sertifikatService.generisiPdf(sertifikat.getLicneInformacije().getJMBG().getValue()), "application/pdf;charset=UTF-8"); 
		    attachment.setDataHandler(new DataHandler(ds));
		    attachment.setFileName("Sertifikat.pdf");
		    mimeMultipart.addBodyPart(attachment);
		    
		    MimeBodyPart attachment1 = new MimeBodyPart();
		    ByteArrayDataSource ds1 = new ByteArrayDataSource(
		    		sertifikatService.generisiXHTML(sertifikat.getLicneInformacije().getJMBG().getValue()), "text/html;charset=UTF-8"); 
		    attachment1.setDataHandler(new DataHandler(ds1));
		    attachment1.setFileName("Sertifikat.htm");
		    mimeMultipart.addBodyPart(attachment1);
		    
		    message.setContent(mimeMultipart);
		    emailSenderService.sendEmail(message);
		} else {
			zahtevRepository.izbrisiZahtev(odgovorNaZahtev.getJMBG());
			MimeMessage message = emailSenderService.createMimeMessage();
			InternetAddress sender = new InternetAddress("mrs_isa_2021_t15_5@hotmail.com");
	        InternetAddress recipient = new InternetAddress(gradjanin.getEmail());
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Odgovor na zahtev");
			message.setSender(sender);
			
		    MimeMultipart mimeMultipart = new MimeMultipart();
		    
		    MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(odgovorNaZahtev.getRazlogOdbijanja());
	        mimeMultipart.addBodyPart(textBodyPart); 
		    message.setContent(mimeMultipart);
		    emailSenderService.sendEmail(message);
		}
	}

	@Override
	public ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg) throws IOException {
		String query = String.format("?s ?p ?o. ?s <http://www.ftn.uns.ac.rs/rdf/zahtev/predicate/jmbg> \"%s\"^^<http://www.w3.org/2001/XMLSchemastring>", jmbg);
		return rdfService.getMetadataJSON(query, "zahtev_" + jmbg, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
	
	@Override
	public ByteArrayInputStream generisiPdf(String jmbg) throws Exception {
		String zahtevXML = zahtevRepository.pronadjiZahtevXmlPoJmbg(jmbg);
		if (zahtevXML == null) {
			throw new ZahtevNijePronadjenoException(jmbg);
		}
		return pdfTransformerService.generatePDF(zahtevXML, XSLFOKonstante.ZAHTEV_XSL_FO);
	}
	
	@Override
	public ByteArrayInputStream generisiXHTML(String jmbg) throws Exception {
		String zahtevXML = zahtevRepository.pronadjiZahtevXmlPoJmbg(jmbg);
		if (zahtevXML == null) {
			throw new ZahtevNijePronadjenoException(jmbg);
		}
		return htmlTransformerService.generateHTML(zahtevXML, XSLKonstante.ZAHTEV_XSL);
	}
	
	@Override
	public List<String> pronadjiSveOsnovnaPretraga(String pretraga) throws Exception {
		return zahtevRepository.pronadjiSveOsnovnaPretraga(pretraga);
	}
}
