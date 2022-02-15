package com.xml.vakcinacija.service.serviceImpl;

import com.xml.vakcinacija.utils.XSLFOKonstante;
import com.xml.vakcinacija.utils.XSLKonstante;
import com.xml.vakcinacija.exception.SertifikatNijePronadjenException;
import com.xml.vakcinacija.exception.SertifikatPostojiException;
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

import java.io.ByteArrayInputStream;
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
    
    @Autowired
	private PDFTransformerService pdfTransformerService;
	
	@Autowired
	private HTMLTransformerService htmlTransformerService;


    @Override
    public void dodajNoviSertifikat(String SertifikatXML) throws Exception {
    	System.out.print(SertifikatXML);
        Sertifikat validanObjekat = (Sertifikat) unmarshallerService.unmarshal(SertifikatXML,
                ContextPutanjeKonstante.CONTEXT_PUTANJA_SERTIFIKAT, XSDPutanjeKonstante.XSD_SERTIFIKAT);
        if (validanObjekat != null) {
            String pronadjenSertifikatXml = sertifikatRepository.pronadjiSertifikatXmlPoJmbg(validanObjekat.getLicneInformacije().getJMBG().getValue());
            if (pronadjenSertifikatXml != null) {
            	throw new SertifikatPostojiException(validanObjekat.getLicneInformacije().getJMBG().getValue());
            }
            sertifikatRepository.saveSertifikatObjekat(validanObjekat);

            try {
                rdfService.save(SertifikatXML, "sertifikat_" + validanObjekat.getLicneInformacije().getJMBG().getValue(), 
                		NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public ByteArrayInputStream nabaviMetaPodatkeJSONPoJmbg(String jmbg) throws IOException {
        String query = String.format("?s ?p ?o. FILTER (?s = <http://www.ftn.uns.ac.rs/rdf/sertifikat/%s>)", jmbg);
        return rdfService.getMetadataJSON(query, "sertifikat_" + jmbg, NamedGraphURIKonstante.IMUNIZACIJA_NAMED_GRAPH);
    }
    
    @Override
	public ByteArrayInputStream generisiPdf(String jmbg) throws Exception {
		String sertifikatXml = sertifikatRepository.pronadjiSertifikatXmlPoJmbg(jmbg);
		if (sertifikatXml == null) {
			throw new SertifikatNijePronadjenException(jmbg);
		}
		return pdfTransformerService.generatePDF(sertifikatXml, XSLFOKonstante.SERTIFIKAT_XSL_FO);
	}
	
	@Override
	public ByteArrayInputStream generisiXHTML(String jmbg) throws Exception {
		String sertifikatXml = sertifikatRepository.pronadjiSertifikatXmlPoJmbg(jmbg);
		if (sertifikatXml == null) {
			throw new SertifikatNijePronadjenException(jmbg);
		}
		return htmlTransformerService.generateHTML(sertifikatXml, XSLKonstante.SERTIFIKAT_XSL);
	}
}
