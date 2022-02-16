package com.xml.vakcinacija.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface RDFService {

	void save(String xml, String rdfXmlFileName, String namedGraphUri) throws IOException, 
		TransformerException, SAXException;
	
	ByteArrayInputStream getMetadataJSON(String sparkQuery, String fileName, String namedGraphUri) throws IOException;

	ByteArrayInputStream getMetadataRDF(String xml) throws TransformerException, IOException, SAXException;
}
