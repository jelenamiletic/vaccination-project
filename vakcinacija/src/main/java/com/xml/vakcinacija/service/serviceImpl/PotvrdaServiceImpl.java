package com.xml.vakcinacija.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

import com.xml.vakcinacija.exception.PotvrdaNijePronadjenoException;
import com.xml.vakcinacija.exception.PotvrdaPostojiException;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.model.zdravstveni_radnik.ZdravstveniRadnik;
import com.xml.vakcinacija.repository.KorisnikRepository;
import com.xml.vakcinacija.repository.PotvrdaRepository;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.PotvrdaService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.TerminService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.QRCodeGenerator;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import com.xml.vakcinacija.utils.XSLKonstante;

@Service
public class PotvrdaServiceImpl implements PotvrdaService{

	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private PotvrdaRepository potvrdaRepository;
	
	@Autowired
	private PDFTransformerService pdfTransformerService;
	
	@Autowired
	private HTMLTransformerService htmlTransformerService;
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;

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
			ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validanObjekat.setQR(QRCodeGenerator.generisiQRCode("http://localhost:8080/potvrda/generisiPdf/" + validanObjekat.getLicneInformacije().getJMBG().getValue()) + 
					"/" + validanObjekat.getInformacijeOVakcinama().get(validanObjekat.getInformacijeOVakcinama().size()-1).getBrojDoze());
			potvrdaRepository.savePotvrdaObjekat(validanObjekat);
			validanObjekat.setAbout("http://www.ftn.uns.ac.rs/rdf/potvrda/" + validanObjekat.getLicneInformacije().getJMBG().getValue() + "_" + 
					validanObjekat.getInformacijeOVakcinama().get(validanObjekat.getInformacijeOVakcinama().size()-1).getBrojDoze());
			
			String noviXml = marshallerService.marshall(validanObjekat, ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, XSDPutanjeKonstante.XSD_POTVRDA);
			
			terminService.postaviIzvrseno(validanObjekat.getLicneInformacije().getJMBG().getValue(), validanObjekat.getInformacijeOVakcinama().size());
			
			Termin termin = terminService.dodajNoviTermin(validanObjekat.getLicneInformacije().getJMBG().getValue(), 
					validanObjekat.getInformacijeOVakcinama().size() + 1, validanObjekat.getVakcinaPrveDveDoze().getValue().toString(), false);
			
			String email = korisnikRepository.pronadjiGradjanina(null, validanObjekat.getLicneInformacije().getJMBG().getValue()).getEmail();
			MimeMessage message = emailSenderService.createMimeMessage();
			InternetAddress sender = new InternetAddress("mrs_isa_2021_t15_5@hotmail.com");
	        InternetAddress recipient = new InternetAddress(email);
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Potvrda o vakcinaciji");
			message.setSender(sender);
			
			String mailText = "Postovani " + zdravstveniRadnik.getPunoIme().getIme() + ",\n"
	        		+ "\tU prilogu se nalazi xhtml i pdf potvrde vakcinacije\n";
			if(termin != null) {
				mailText +=  "Dodeljen vam je termin za vakcinaciju:\n\t\t" + termin.getDatum()
				 + "\npre nego sto odete da se vakcinisete morate popuniti saglasnost na veb servisu.";
			}
			
			MimeMultipart mimeMultipart = new MimeMultipart();
		    
		    MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(mailText);
	        mimeMultipart.addBodyPart(textBodyPart);
	        
			MimeBodyPart attachment = new MimeBodyPart();
		    ByteArrayDataSource ds = new ByteArrayDataSource(generisiPdf(validanObjekat.getLicneInformacije().getJMBG().getValue(), validanObjekat.getInformacijeOVakcinama().size()), "application/pdf"); 
		    attachment.setDataHandler(new DataHandler(ds));
		    attachment.setFileName("Potvrda.pdf");
		    mimeMultipart.addBodyPart(attachment);
		    
		    MimeBodyPart attachment1 = new MimeBodyPart();
		    ByteArrayDataSource ds1 = new ByteArrayDataSource(generisiXHTML(validanObjekat.getLicneInformacije().getJMBG().getValue(), validanObjekat.getInformacijeOVakcinama().size()), "text/html"); 
		    attachment1.setDataHandler(new DataHandler(ds1));
		    attachment1.setFileName("Potvrda.htm");
		    mimeMultipart.addBodyPart(attachment1);
		    
		    message.setContent(mimeMultipart);
		    emailSenderService.sendEmail(message);
			
			try {
				rdfService.save(noviXml, "potvrda_" + validanObjekat.getLicneInformacije().getJMBG().getValue()
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
	public List<Potvrda> pronadjiPotvrdaPoJmbg(String jmbg) throws Exception {
		List<Potvrda> potvrda = potvrdaRepository.pronadjiPotvrdaPoJmbg(jmbg);
		if (potvrda == null) {
			throw new PotvrdaNijePronadjenoException(jmbg);
		}
		return potvrda;
	}
	
	@Override
	public Potvrda dobaviPoslednjuPotvrduPoJmbg(String jmbg) throws Exception {
		Potvrda potvrda = potvrdaRepository.dobaviPoslednjuPotvrduPoJmbg(jmbg);
		if (potvrda == null) {
			throw new PotvrdaNijePronadjenoException(jmbg);
		}
		return potvrda;
	}
	
	@Override
	public ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg, int brojDoze) throws IOException {
		String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/potvrda/%s_%d>)", jmbg, brojDoze);
		return rdfService.getMetadataJSON(query, "potvrda_" + jmbg + "_" + brojDoze, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}
	
	@Override
	public ByteArrayInputStream nabaviMetaPodatkeRDFPoJmbg(String jmbg, int brojDoze) throws Exception {
		String potvrdaXml = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(jmbg, brojDoze);
		if (potvrdaXml == null) {
			throw new PotvrdaNijePronadjenoException(jmbg);
		}
		return rdfService.getMetadataRDF(potvrdaXml);
	}
	
	@Override
	public ByteArrayInputStream generisiXHTML(String id, int brojDoze) throws Exception {
		String potvrda = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(id, brojDoze);
		if (potvrda == null || potvrda.equals("")) {
			return null;
		}
		return htmlTransformerService.generateHTML(potvrda, XSLKonstante.POTVRDA_XSL);
	}

	@Override
	public ByteArrayInputStream generisiPdf(String id, int brojDoze) throws Exception {
		String potvrda = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(id, brojDoze);
		if (potvrda == null || potvrda.equals("")) {
			return null;
		}
		return pdfTransformerService.generatePDF(potvrda, com.xml.vakcinacija.utils.XSLFOKonstante.POTVRDA_XSL_FO);
	}
	
	@Override
	public String pronadjiSveOsnovnaPretraga(String pretraga) throws Exception {
		String rez = "<Potvrde>";
		
		List<String> interesovanja = potvrdaRepository.pronadjiSveOsnovnaPretraga(pretraga);
		if(interesovanja != null && interesovanja.size() > 0) {
			for (String interesovanje : potvrdaRepository.pronadjiSveOsnovnaPretraga(pretraga)) {
				rez += interesovanje;
			}
		}
		
		rez+= "</Potvrde>";
		
		return rez;
	}
	
	@Override
	public String pronadjiSveNaprednaPretraga(String ime, String prezime, String jmbg, String pol) throws Exception {
		
		String rezultat = "<Potvrde>";
		
		List<Potvrda> interesovanja = potvrdaRepository.pronadjiSve();
		
		if(interesovanja != null && interesovanja.size() > 0) {
			for (Potvrda potvrda : potvrdaRepository.pronadjiSve()) {
				
				if(ime.equals("NEMA") || ime == null || potvrda.getLicneInformacije().getPunoIme().getIme().equals(ime)) {
					
					if(prezime.equals("NEMA") || prezime == null || potvrda.getLicneInformacije().getPunoIme().getPrezime().equals(prezime)) {
						
						if(jmbg.equals("NEMA") || jmbg == null || potvrda.getLicneInformacije().getJMBG().getValue().equals(jmbg)) {
							
							if(pol.equals("NEMA") || pol == null || potvrda.getLicneInformacije().getPol().toString().equals(pol)) {
								
								rezultat += marshallerService.marshall(potvrda, ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, XSDPutanjeKonstante.XSD_POTVRDA);
							}
						}
					}
				}
			}
		}	
		
		return rezultat + "</Potvrde>";
	}
}
