package com.xml.sluzbenik.repository;

import java.util.ArrayList;
import java.util.List;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.sluzbenik.exist.ExistRetrieve;
import com.xml.sluzbenik.exist.ExistStore;
import com.xml.sluzbenik.model.sluzbenik.Sluzbenik;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.UnmarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XMLCollectionIdKonstante;
import com.xml.sluzbenik.utils.XMLNamespaceKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@Repository
public class SluzbenikRepository {
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveSluzbenikXml(String xml, String email) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SLUZBENIK_KORISNICI, email.toLowerCase(), xml);
    }
	
	public void saveSluzbenikObjekat(Sluzbenik sluzbenik) throws Exception {
		String xml = marshallerService.marshall(sluzbenik, ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, 
				XSDPutanjeKonstante.XSD_SLUZBENIK);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SLUZBENIK_KORISNICI, sluzbenik.getEmail().toLowerCase(), xml);
	}
	
	public List<Sluzbenik> pronadjiSve() throws Exception {
		String xPathIzraz = "//Sluzbenik";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SLUZBENIK_KORISNICI, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SLUZBENIK);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Sluzbenik> sluzbenici = new ArrayList<Sluzbenik>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            sluzbenici.add((Sluzbenik) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, XSDPutanjeKonstante.XSD_SLUZBENIK));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return sluzbenici;
	}

	public String pronadjiSluzbenikaXml(String email) throws Exception {
        String xPathIzraz = String.format("/Sluzbenik[Email = '%s']", email);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SLUZBENIK_KORISNICI, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SLUZBENIK);
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
	
	public Sluzbenik pronadjiSluzbenika(String email) throws Exception {
		String xml = this.pronadjiSluzbenikaXml(email);
		if (xml != null) {
			return (Sluzbenik) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_SLUZBENIK, 
				XSDPutanjeKonstante.XSD_SLUZBENIK);
		}
		return null;
	}
}
