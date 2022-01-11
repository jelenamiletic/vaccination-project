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
import com.xml.vakcinacija.model.saglasnost.Saglasnost;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Repository
public class SaglasnostRepository {
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;

	public void saveSaglasnostXml(String xml, String jmbg) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, jmbg, xml);
    }
	
	public void saveSaglasnostObjekat(Saglasnost saglasnost) throws Exception {
		String xml = marshallerService.marshall(saglasnost, ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, 
				XSDPutanjeKonstante.XSD_SAGLASNOST);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, saglasnost.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo(), xml);
	}
	
	public List<Saglasnost> pronadjiSve() throws Exception {
		String xPathIzraz = "//Saglasnost";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SAGLASNOST);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Saglasnost> listaSaglasnosti = new ArrayList<Saglasnost>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaSaglasnosti.add((Saglasnost) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, XSDPutanjeKonstante.XSD_SAGLASNOST));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaSaglasnosti;
	}
	
	public String pronadjiSaglasnostXmlPoJmbg(String jmbg, boolean strani) throws Exception {
        String xPathIzraz;
        if(strani)
        { 
        	xPathIzraz = String.format("/Saglasnost[PacijentSaglasnost/LicneInformacije/Drzavljanstvo/StranoDrzavljanstvo/BrojPasosa = '%s']" , jmbg);
        }
        else
        {
        	xPathIzraz = String.format("/Saglasnost[PacijentSaglasnost/LicneInformacije/Drzavljanstvo/RepublikaSrbija/JMBG = '%s']" , jmbg);
        }
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SAGLASNOST);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String saglasnost = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            saglasnost = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return saglasnost;
    }
	
	public Saglasnost pronadjiSaglasnostPoJmbg(String jmbg) throws Exception {
		String xml = this.pronadjiSaglasnostXmlPoJmbg(jmbg, false);
		if (xml != null) {
			return (Saglasnost) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, 
				XSDPutanjeKonstante.XSD_SAGLASNOST);
		}
		return null;
	}
}
