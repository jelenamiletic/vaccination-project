package com.xml.vakcinacija.repository;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.vakcinacija.exist.ExistRetrieve;
import com.xml.vakcinacija.exist.ExistStore;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;

@Repository
public class InteresovanjeRepository {

	public void save(String xml, String jmbg) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_INTERESOVANJE, jmbg, xml);
    }
	
	public String pronadjiInteresovanjePoJMBG(String jmbg) throws Exception {
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
}
