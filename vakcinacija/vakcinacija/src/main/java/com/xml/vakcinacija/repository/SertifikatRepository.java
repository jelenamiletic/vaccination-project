package com.xml.vakcinacija.repository;

import com.xml.vakcinacija.exist.ExistRetrieve;
import com.xml.vakcinacija.exist.ExistStore;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SertifikatRepository {
    @Autowired
    private MarshallerService marshallerService;

    @Autowired
    private UnmarshallerService unmarshallerService;

    public void saveSertifikatXML(String xml, String jmbg) throws Exception{
        ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SERTIFIKAT, jmbg, xml);
    }

    public void saveSertifikatObjekat(Sertifikat sertifikat) throws Exception {
        String xml = marshallerService.marshall(sertifikat, ContextPutanjeKonstante.CONTEXT_PUTANJA_SERTIFIKAT,
                XSDPutanjeKonstante.XSD_SERTIFIKAT);
        ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SERTIFIKAT, sertifikat.getLicneInformacije().getJMBG().getValue(), xml);
    }

    public List<Sertifikat> pronadjiSve() throws Exception {
        String xPathIzraz = "//Sertifikat";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SERTIFIKAT,
                xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SERTIFIKAT);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Sertifikat> listaSertifikata = new ArrayList<Sertifikat>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaSertifikata.add((Sertifikat) unmarshallerService.unmarshal(res.getContent().toString(),
                    ContextPutanjeKonstante.CONTEXT_PUTANJA_SERTIFIKAT, XSDPutanjeKonstante.XSD_SERTIFIKAT));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaSertifikata;
    }

    public String pronadjiSertifikatXmlPoJmbg(String jmbg) throws Exception {
        String xPathIzraz = String.format("/Sertifikat[LicneInformacije/JMBG = '%s']" , jmbg);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SERTIFIKAT,
                xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SERTIFIKAT);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String sertifikat = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            sertifikat = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return sertifikat;
    }

    public Sertifikat pronadjiSertifikatPoJmbg(String jmbg) throws Exception {
        String xml = this.pronadjiSertifikatXmlPoJmbg(jmbg);
        if (xml != null) {
            return (Sertifikat) unmarshallerService.unmarshal(xml,
                    ContextPutanjeKonstante.CONTEXT_PUTANJA_SERTIFIKAT,
                    XSDPutanjeKonstante.XSD_SERTIFIKAT);
        }
        return null;
    }

}
