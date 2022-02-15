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

import com.xml.vakcinacija.exception.InteresovanjeNijePronadjenoException;
import com.xml.vakcinacija.exception.InteresovanjePostojiException;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.repository.InteresovanjeRepository;
import com.xml.vakcinacija.service.InteresovanjeService;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.TerminService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import com.xml.vakcinacija.utils.XSLKonstante;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {
		
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	@Autowired
	private RDFService rdfService;
	
	@Autowired
	private InteresovanjeRepository interesovanjeRepository;
	
	@Autowired
	private TerminService terminService;
	
	@Autowired
	private PDFTransformerService pdfTransformerService;
	
	@Autowired
	private HTMLTransformerService htmlTransformerService;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public void dodajNovoInteresovanje(String interesovanjeXML) throws Exception {
		Interesovanje validanObjekat = (Interesovanje) unmarshallerService.unmarshal(interesovanjeXML, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, XSDPutanjeKonstante.XSD_INTERESOVANJE);
		if (validanObjekat != null) {
			String pronadjenoInteresovanjeXml = interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG().getValue());
			if (pronadjenoInteresovanjeXml != null) {
				throw new InteresovanjePostojiException(validanObjekat.getLicneInformacije().getJMBG().getValue());
			}
			validanObjekat.setDatumPodnosenja();
			Gradjanin gradjanin = (Gradjanin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validanObjekat.getLicneInformacije().getDrzavljanstvo().setValue(gradjanin.getDrzavljanstvo());
			validanObjekat.getLicneInformacije().getPunoIme().setIme(gradjanin.getPunoIme().getIme());
			validanObjekat.getLicneInformacije().getPunoIme().setPrezime(gradjanin.getPunoIme().getPrezime());
			interesovanjeRepository.saveInteresovanjeObjekat(validanObjekat);
			
			Termin termin = terminService.dodajNoviTermin(gradjanin.getJMBG(), 1, validanObjekat.getVakcina().getValue().toString(), true);
			
			MimeMessage message = emailSenderService.createMimeMessage();
			InternetAddress sender = new InternetAddress("mrs_isa_2021_t15_5@hotmail.com");
	        InternetAddress recipient = new InternetAddress(gradjanin.getEmail());
			message.setRecipient(Message.RecipientType.TO, recipient);
			message.setSubject("Uspesno iskazano interesovanje");
			message.setSender(sender);
			
			String mailText = "Postovani " + gradjanin.getPunoIme().getIme() + ",\n"
	        		+ "\tVase interesovanje je uspesno iskazano. U prilogu se nalazi xhtml i pdf vaseg interesovanja\n";
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
		    attachment.setFileName("Interesovanje.pdf");
		    mimeMultipart.addBodyPart(attachment);
		    
		    MimeBodyPart attachment1 = new MimeBodyPart();
		    ByteArrayDataSource ds1 = new ByteArrayDataSource(generisiXHTML(gradjanin.getJMBG()), "text/html"); 
		    attachment1.setDataHandler(new DataHandler(ds1));
		    attachment1.setFileName("Interesovanje.htm");
		    mimeMultipart.addBodyPart(attachment1);
		    
		    message.setContent(mimeMultipart);
		    emailSenderService.sendEmail(message);
			
			try {
				rdfService.save(interesovanjeXML, "interesovanje_" + validanObjekat.getLicneInformacije().getJMBG().getValue(), 
						NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public List<Interesovanje> pronadjiSve() throws Exception {
		return interesovanjeRepository.pronadjiSve();
	}

	@Override
	public Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception {
		Interesovanje interesovanje = interesovanjeRepository.pronadjiInteresovanjePoJmbg(jmbg);
		if (interesovanje == null) {
			throw new InteresovanjeNijePronadjenoException(jmbg);
		}
		return interesovanje;
	}
	
	@Override
	public void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException {
		String query = String.format("?s ?p ?o. ?s <http://www.ftn.uns.ac.rs/rdf/interesovanje/predicate/jmbg> \"%s\"^^<http://www.w3.org/2001/XMLSchemastring>", jmbg);
		rdfService.getMetadataXML(query, "interesovanje_" + jmbg, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
	}

	@Override
	public ByteArrayInputStream generisiXHTML(String jmbg) throws Exception {
		String interesovanje = interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(jmbg);
		if (interesovanje == null) {
			throw new Exception();
		}
		return htmlTransformerService.generateHTML(interesovanje, XSLKonstante.INTERESOVANJE_XSL);
	}

	@Override
	public ByteArrayInputStream generisiPdf(String jmbg) throws Exception {
		String interesovanje = interesovanjeRepository.pronadjiInteresovanjeXmlPoJmbg(jmbg);
		if (interesovanje == null) {
			throw new Exception();
		}
		return pdfTransformerService.generatePDF(interesovanje, com.xml.vakcinacija.utils.XSLFOKonstante.INTERESOVANJE_XSL_FO);
	}
}
