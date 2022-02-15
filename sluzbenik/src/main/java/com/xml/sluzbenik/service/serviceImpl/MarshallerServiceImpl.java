package com.xml.sluzbenik.service.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.xml.sluzbenik.service.MarshallerService;

@Service
public class MarshallerServiceImpl implements MarshallerService {

	@Override
	public String marshall(Object objekat, String contextPath, String xsdPutanja) throws SAXException {
		ByteArrayOutputStream rez = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Marshaller marshaller = context.createMarshaller();
			if(xsdPutanja != null) {
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	            Schema xsd = sf.newSchema(new File(xsdPutanja));
	            marshaller.setSchema(xsd);
			}
            marshaller.marshal(objekat, rez);
        } catch (JAXBException e) {
        	e.printStackTrace();
        }
		return new String(rez.toByteArray(), StandardCharsets.UTF_8);
	}
	
}
