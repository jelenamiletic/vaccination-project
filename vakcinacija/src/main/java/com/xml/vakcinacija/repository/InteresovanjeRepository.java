package com.xml.vakcinacija.repository;

import java.util.ArrayList;
import java.util.List;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.vakcinacija.exist.ExistRetrieve;
import com.xml.vakcinacija.exist.ExistStore;
import com.xml.vakcinacija.model.interesovanje.Interesovanje;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Repository
public class InteresovanjeRepository {
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;

	public void saveInteresovanjeXml(String xml, String jmbg) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, jmbg, xml);
    }
	
	public void saveInteresovanjeObjekat(Interesovanje interesovanje) throws Exception {
		String xml = marshallerService.marshall(interesovanje, ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, 
				XSDPutanjeKonstante.XSD_INTERESOVANJE);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, interesovanje.getLicneInformacije().getJMBG().getValue(), xml);
	}
	
	public List<Interesovanje> pronadjiSve() throws Exception {
		String xPathIzraz = "//Interesovanje";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Interesovanje> listaInteresovanja = new ArrayList<Interesovanje>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaInteresovanja.add((Interesovanje) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, XSDPutanjeKonstante.XSD_INTERESOVANJE));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaInteresovanja;
	}
	
	public List<String> pronadjiSveOsnovnaPretraga(String pretraga) throws Exception {
		String xPathIzraz = "//Interesovanje";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<String> listaInteresovanja = new ArrayList<String>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            
            String xml = res.getContent().toString();
            if(xml.contains(pretraga)) {
            	listaInteresovanja.add(xml);
            }
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaInteresovanja;
	}
	
	public String pronadjiInteresovanjeXmlPoJmbg(String jmbg) throws Exception {
        String xPathIzraz = String.format("/Interesovanje[LicneInformacije/JMBG = '%s']" , jmbg);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String interesovanje = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            interesovanje = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return interesovanje;
    }
	
	public Interesovanje pronadjiInteresovanjePoJmbg(String jmbg) throws Exception {
		String xml = this.pronadjiInteresovanjeXmlPoJmbg(jmbg);
		if (xml != null) {
			return (Interesovanje) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_INTERESOVANJE, 
				XSDPutanjeKonstante.XSD_INTERESOVANJE);
		}
		return null;
	}
}
