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
import com.xml.vakcinacija.model.zahtev.Zahtev;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Repository
public class ZahtevRepository {

	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveZahtevXml(String xml, String jmbg) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_ZAHTEV, jmbg, xml);
    }
	
	public void saveZahtevObjekat(Zahtev zahtev) throws Exception {
		String xml = marshallerService.marshall(zahtev, ContextPutanjeKonstante.CONTEXT_PUTANJA_ZAHTEV, 
				XSDPutanjeKonstante.XSD_ZAHTEV);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_ZAHTEV, zahtev.getPodnosilac().getJMBG().getValue(), xml);
	}
	
	public List<Zahtev> pronadjiSve() throws Exception {
		String xPathIzraz = "//Zahtev";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_ZAHTEV, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_ZAHTEV);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Zahtev> listaZahteva = new ArrayList<Zahtev>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaZahteva.add((Zahtev) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_ZAHTEV, XSDPutanjeKonstante.XSD_ZAHTEV));
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
	
	public String pronadjiZahtevXmlPoJmbg(String jmbg) throws Exception {
        String xPathIzraz = String.format("/Zahtev[Podnosilac/JMBG = '%s']" , jmbg);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_ZAHTEV, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_ZAHTEV);
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
	
	public Zahtev pronadjiZahtevPoJmbg(String jmbg) throws Exception {
		String xml = this.pronadjiZahtevXmlPoJmbg(jmbg);
		if (xml != null) {
			return (Zahtev) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_ZAHTEV, 
				XSDPutanjeKonstante.XSD_ZAHTEV);
		}
		return null;
	}
}
