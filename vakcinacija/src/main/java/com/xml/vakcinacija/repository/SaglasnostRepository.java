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

	public void saveSaglasnostXml(String xml, String id) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, id, xml);
    }
	
	public int saveSaglasnostObjekat(Saglasnost saglasnost) throws Exception {
		String xml = marshallerService.marshall(saglasnost, ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, 
				XSDPutanjeKonstante.XSD_SAGLASNOST);
		String id = saglasnost.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo();
		int index = getNextDocumentIndex(id);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, id + '_' + Integer.toString(index), xml);
		return index;
	}
	
	public int editSaglasnostObjekat(Saglasnost saglasnost) throws Exception {
		String xml = marshallerService.marshall(saglasnost, ContextPutanjeKonstante.CONTEXT_PUTANJA_SAGLASNOST, 
				XSDPutanjeKonstante.XSD_SAGLASNOST);
		String id = saglasnost.getPacijentSaglasnost().getLicneInformacije().getIdFromDrzavljanstvo();
		int index = getNextDocumentIndex(id) - 1;
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, id + '_' + Integer.toString(index), xml);
		return index;
	}
	
	public int getNextDocumentIndex(String id) {
	        int index = 1;
	        String fullId = id + "_" + index;
	        String doc = "";
    		
	        try {
	            doc = ExistRetrieve.nabaviResurs(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, fullId);

	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        while (!doc.equals("")) {
	            index++;
	            fullId = id + "_" + index;
	            try {
	            	doc = ExistRetrieve.nabaviResurs(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, fullId);

	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
	        }
	        return index;
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
	
	public List<String> pronadjiSveOsnovnaPretraga(String pretraga) throws Exception {
		String xPathIzraz = "//Saglasnost";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_SAGLASNOST);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<String> listaSaglasnosti = new ArrayList<String>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            
            String xml = res.getContent().toString();
            if(xml.contains(pretraga)) {
            	listaSaglasnosti.add(xml);
            }
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
	
	public List<Saglasnost> pronadjiSaglasnostXmlPoFullId(String id, boolean jmbg) throws Exception {
        String xPathIzraz;
        
        if (jmbg) 
        {
        	xPathIzraz = String.format("/Saglasnost[PacijentSaglasnost/LicneInformacije/Drzavljanstvo/RepublikaSrbija/JMBG = '%s']" , id);
        } 
        else 
        {
        	xPathIzraz = String.format("/Saglasnost[PacijentSaglasnost/LicneInformacije/Drzavljanstvo/StranoDrzavljanstvo/BrojPasosa = '%s']" , id);
        }
        
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(
        		XMLCollectionIdKonstante.COLLECTION_ID_SAGLASNOST, 
        		xPathIzraz,
        		XMLNamespaceKonstante.NAMESPACE_SAGLASNOST);
        
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
	
	public List<Saglasnost> pronadjiSaglasnostPoJmbgIliBrPasosa(String id) throws Exception {
		boolean jmbg = false;
		
		if(id.length() == 13)
		{
			jmbg = true;
		}
		return this.pronadjiSaglasnostXmlPoFullId(id, jmbg);
	}
}
