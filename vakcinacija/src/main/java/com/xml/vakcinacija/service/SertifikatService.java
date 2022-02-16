package com.xml.vakcinacija.service;

import com.xml.vakcinacija.model.sertifikat.Sertifikat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface SertifikatService {
    void dodajNoviSertifikat(String SertifikatXML) throws Exception;

    List<Sertifikat> pronadjiSve() throws Exception;

    Sertifikat pronadjiSertifikatPoJmbg(String jmbg) throws Exception;

    ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg) throws IOException;
    
    ByteArrayInputStream nabaviMetaPodatkeRDFPoJmbg(String jmbg) throws IOException, Exception;

	ByteArrayInputStream generisiXHTML(String jmbg) throws Exception;

	ByteArrayInputStream generisiPdf(String jmbg) throws Exception;

	String pronadjiSveOsnovnaPretraga(String pretraga) throws Exception;
	
	String pronadjiSveNaprednaPretraga(String ime, String prezime, String jmbg, String pol) throws Exception;
}
