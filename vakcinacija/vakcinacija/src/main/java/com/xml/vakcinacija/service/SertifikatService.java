package com.xml.vakcinacija.service;

import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;

import java.io.IOException;
import java.util.List;

public interface SertifikatService {
    void dodajNoviSertifikat(String SertifikatXML) throws Exception;

    List<Sertifikat> pronadjiSve() throws Exception;

    Sertifikat pronadjiSertifikatPoJmbg(String jmbg) throws Exception;

    void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException;

}