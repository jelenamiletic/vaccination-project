package com.xml.vakcinacija.service;

import org.xml.sax.SAXException;

public interface UnmarshallerService {

	Object unmarshal(String xml, String xsdPutanja) throws SAXException;
}
