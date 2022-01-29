package com.xml.sluzbenik.service;

import org.xml.sax.SAXException;

public interface UnmarshallerService {

	Object unmarshal(String xml, String contextPath, String xsdPutanja) throws SAXException;
}
