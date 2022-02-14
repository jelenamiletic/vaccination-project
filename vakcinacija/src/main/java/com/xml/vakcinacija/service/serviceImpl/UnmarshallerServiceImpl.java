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
	
	@Override
	public Object unmarshal(String xml, String contextPath, String xsdPutanja) throws SAXException {
		try {
			JAXBContext context = JAXBContext.newInstance(contextPath);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			if(xsdPutanja != null){
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	            Schema xsd = sf.newSchema(new File(xsdPutanja));
	            unmarshaller.setSchema(xsd);
			}
            return unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
        	e.printStackTrace();
        }
		return null;
	}
}
