package com.xml.vakcinacija.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.xml.vakcinacija.service.UnmarshallerService;

@Service
public class UnmarshallerServiceImpl implements UnmarshallerService {
	
	private static Unmarshaller unmarshaller;
	
	static {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance("com.xml.vakcinacija.model");
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object unmarshal(String xml, String xsdPutanja) throws SAXException {
		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema xsdSema = sf.newSchema(new File(xsdPutanja));
            unmarshaller.setSchema(xsdSema);
            return unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
}
