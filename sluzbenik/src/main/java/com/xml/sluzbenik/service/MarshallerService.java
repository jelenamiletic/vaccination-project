package com.xml.sluzbenik.service;

import org.xml.sax.SAXException;

public interface MarshallerService {

	String marshall(Object objekat, String contextPath, String xsdPutanja) throws SAXException;
}
