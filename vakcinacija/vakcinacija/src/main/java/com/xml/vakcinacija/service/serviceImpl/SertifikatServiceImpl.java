package com.xml.vakcinacija.service.serviceImpl;

import com.xml.vakcinacija.exception.PotvrdaPostojiException;
import com.xml.vakcinacija.exception.SertifikatNijePronadjenException;
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;
import com.xml.vakcinacija.repository.SertifikatRepository;
import com.xml.vakcinacija.service.RDFService;
import com.xml.vakcinacija.service.SertifikatService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.NamedGraphURIKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SertifikatServiceImpl implements SertifikatService {

    @Autowired
    private UnmarshallerService unmarshallerService;

    @Autowired
    private RDFService rdfService;

    @Autowired
    private SertifikatRepository sertifikatRepository;


    @Override
    public void dodajNoviSertifikat(String SertifikatXML) throws Exception {
        Sertifikat validanObjekat = (Sertifikat) unmarshallerService.unmarshal(SertifikatXML,
                ContextPutanjeKonstante.CONTEXT_PUTANJA_SERTIFIKAT, XSDPutanjeKonstante.XSD_SERTIFIKAT);
        if (validanObjekat != null) {
            String pronadjenSertifikatXml = sertifikatRepository.pronadjiSertifikatXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG().getValue());
            sertifikatRepository.saveSertifikatObjekat(validanObjekat);

//            try {
//                rdfService.save(SertifikatXML, "sertifikat_" + validanObjekat.getLicneInformacije().getJMBG().getValue()
//                        , NamedGraphURIKonstante.POTVRDA_NAMED_GRAPH);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public List<Sertifikat> pronadjiSve() throws Exception {
        return sertifikatRepository.pronadjiSve();
    }

    @Override
    public Sertifikat pronadjiSertifikatPoJmbg(String jmbg) throws Exception {
        Sertifikat sertifikat = sertifikatRepository.pronadjiSertifikatPoJmbg(jmbg);
        if(sertifikat == null){
            throw new SertifikatNijePronadjenException(jmbg);
        }
        return sertifikat;
    }

    @Override
    public void nabaviMetaPodatkeXmlPoJmbg(String jmbg) throws IOException {
        String query = String.format("?s ?p ?o. ?s FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/sertifikat/%s_%d>)", jmbg);
        rdfService.getMetadataXML(query, "sertifikat_" + jmbg, NamedGraphURIKonstante.SERTIFIKAT_NAMED_GRAPH);
    }
}
