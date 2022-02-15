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
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;
import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.model.zdravstveni_radnik.ZdravstveniRadnik;
import com.xml.vakcinacija.repository.PotvrdaRepository;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.PotvrdaService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.TerminService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
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
			ZdravstveniRadnik gradjanin = (ZdravstveniRadnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			potvrdaRepository.savePotvrdaObjekat(validanObjekat);
			
			terminService.postaviIzvrseno(validanObjekat.getLicneInformacije().getJMBG().getValue(), validanObjekat.getInformacijeOVakcinama().size());
			
			Termin termin = terminService.dodajNoviTermin(validanObjekat.getLicneInformacije().getJMBG().getValue(), 
					validanObjekat.getInformacijeOVakcinama().size() + 1, validanObjekat.getVakcinaPrveDveDoze().getValue().toString(), false);
			
			MimeMessage message = emailSenderService.createMimeMessage();
			InternetAddress sender = new InternetAddress("mrs_isa_2021_t15_5@hotmail.com");
	        InternetAddress recipient = new InternetAddress(gradjanin.getEmail());
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Potvrda o vakcinaciji");
			message.setSender(sender);
			
			String mailText = "Postovani " + gradjanin.getPunoIme().getIme() + ",\n"
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
		    ByteArrayDataSource ds = new ByteArrayDataSource(generisiPdf(gradjanin.getJMBG()), "application/pdf"); 
		    attachment.setDataHandler(new DataHandler(ds));
		    attachment.setFileName("Potvrda.pdf");
		    mimeMultipart.addBodyPart(attachment);
		    
		    MimeBodyPart attachment1 = new MimeBodyPart();
		    ByteArrayDataSource ds1 = new ByteArrayDataSource(generisiXHTML(gradjanin.getJMBG()), "text/html"); 
		    attachment1.setDataHandler(new DataHandler(ds1));
		    attachment1.setFileName("Potvrda.htm");
		    mimeMultipart.addBodyPart(attachment1);
		    
		    message.setContent(mimeMultipart);
		    emailSenderService.sendEmail(message);
			
			try {
				rdfService.save(PotvrdaXML, "potvrda_" + validanObjekat.getLicneInformacije().getJMBG().getValue()
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
	public ByteArrayInputStream generisiXHTML(String jmbg) throws Exception {
		String potvrda = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(jmbg, 1);
		if (potvrda == null) {
			throw new Exception();
		}
		return htmlTransformerService.generateHTML(potvrda, XSLKonstante.POTVRDA_XSL);
	}

	@Override
	public ByteArrayInputStream generisiPdf(String jmbg) throws Exception {
		String potvrda = potvrdaRepository.pronadjiPotvrdaXmlPoJmbg(jmbg, 1);
		if (potvrda == null) {
			throw new Exception();
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
					
					if(prezime.equals("NEMA") || prezime == null || potvrda.getLicneInformacije().getPunoIme().getIme().equals(ime)) {
						
						if(jmbg.equals("NEMA") || jmbg == null || potvrda.getLicneInformacije().getJMBG().getValue().equals(jmbg)) {
							
							if(pol.equals("NEMA") || pol == null || potvrda.getLicneInformacije().getPol().toString().equals(ime)) {
								
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
