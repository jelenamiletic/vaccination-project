package com.xml.sluzbenik.service;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface RDFService {

	void save(String xml, String rdfXmlFileName, String namedGraphUri) throws IOException, 
		TransformerException, SAXException;
	
	void getMetadataXML(String sparkQuery, String fileName, String namedGraphUri) throws IOException;
}
