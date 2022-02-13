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
import com.xml.vakcinacija.model.termin.Termin;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;

@Repository
public class TerminRepository {
	
	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveTerminXml(String xml, String jmbg, int brojDoze) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_TERMIN, jmbg + "_" + brojDoze, xml);
    }
	
	public void saveTerminObjekat(Termin termin) throws Exception {
		String xml = marshallerService.marshall(termin, ContextPutanjeKonstante.CONTEXT_PUTANJA_TERMIN, 
				null);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_TERMIN, termin.getJmbg() + "_" + termin.getBrojDoze(), xml);
	}
	
	public List<Termin> pronadjiSve() throws Exception {
		String xPathIzraz = "//Termin";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_TERMIN, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_TERMIN);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Termin> listaZahteva = new ArrayList<Termin>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaZahteva.add((Termin) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_TERMIN, null));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaZahteva;
	}
	
	public List<Termin> pronadjiSvePoJmbg(String jmbg) throws Exception {
		String xPathIzraz = String.format("//Termin[jmbg = '%s']", jmbg);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_TERMIN, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_TERMIN);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Termin> listaZahteva = new ArrayList<Termin>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaZahteva.add((Termin) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_TERMIN, null));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaZahteva;
	}
	
	public String pronadjiTerminXmlPoJmbgIDozi(String jmbg, int brojDoze) throws Exception {
        String xPathIzraz = String.format("/Termin[jmbg = '%s'and brojDoze = %d]" , jmbg, brojDoze);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_TERMIN, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_TERMIN);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String zahtev = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            zahtev = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return zahtev;
    }
	
	public Termin pronadjiTerminPoJmbgIDozi(String jmbg, int brojDoze) throws Exception {
		String xml = this.pronadjiTerminXmlPoJmbgIDozi(jmbg, brojDoze);
		if (xml != null) {
			return (Termin) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_TERMIN, 
				null);
		}
		return null;
	}

}
