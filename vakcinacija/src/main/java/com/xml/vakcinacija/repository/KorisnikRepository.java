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
import com.xml.vakcinacija.model.Korisnik;
import com.xml.vakcinacija.model.gradjanin.Gradjanin;
import com.xml.vakcinacija.model.zdravstveni_radnik.ZdravstveniRadnik;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Repository
public class KorisnikRepository {

	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveKorisnikXml(String xml, String email, String collectionId) throws Exception {
		ExistStore.save(collectionId, email.toLowerCase(), xml);
    }
	
	public void saveKorisnikObjekat(Korisnik korisnik) throws Exception {
		if (korisnik instanceof Gradjanin) {
			String xml = marshallerService.marshall(korisnik, ContextPutanjeKonstante.CONTEXT_PUTANJA_GRADJANIN, 
					XSDPutanjeKonstante.XSD_GRADJANIN);
			ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_GRADJANIN, korisnik.getEmail().toLowerCase(), xml);
		} else if (korisnik instanceof ZdravstveniRadnik) {
			String xml = marshallerService.marshall(korisnik, ContextPutanjeKonstante.CONTEXT_PUTANJA_ZDRAVSTVENI_RADNIK, 
					XSDPutanjeKonstante.XSD_ZDRAVSTVENI_RADNIK);
			ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_ZDRAVSTVENI_RADNINK, korisnik.getEmail().toLowerCase(), xml);
		}
	}
	
	public List<Gradjanin> pronadjiSveGradjane() throws Exception {
		String xPathIzraz = "//Gradjanin";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_GRADJANIN, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_GRADJANIN);
        List<Gradjanin> gradjani = new ArrayList<Gradjanin>();
        
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            gradjani.add((Gradjanin) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_GRADJANIN, XSDPutanjeKonstante.XSD_GRADJANIN));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }
        return gradjani;
	}
	
	public List<ZdravstveniRadnik> pronadjiSveZdravstveneRadnike() throws Exception {
		String xPathIzraz = "//ZdravstveniRadnik";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_ZDRAVSTVENI_RADNINK, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_ZDRAVSTVENI_RADNIK);
        List<ZdravstveniRadnik> zdravstveniRadnici = new ArrayList<ZdravstveniRadnik>();
        
		if (rezultat == null)
            return null;
		
		ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            zdravstveniRadnici.add((ZdravstveniRadnik) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_GRADJANIN, XSDPutanjeKonstante.XSD_GRADJANIN));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }
        
        return zdravstveniRadnici;
	}

	public Gradjanin pronadjiGradjanina(String email) throws Exception {
		String xPathIzraz = String.format("/Gradjanin[ct:Email = '%s']", email);
		String xml = this.pronadjiKorisnikaXml(XMLCollectionIdKonstante.COLLECTION_ID_GRADJANIN, xPathIzraz, 
				XMLNamespaceKonstante.NAMESPACE_GRADJANIN);
		if (xml != null) {
			return (Gradjanin) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_GRADJANIN, 
				XSDPutanjeKonstante.XSD_GRADJANIN);
		}
		return null;
	}
	
	public ZdravstveniRadnik pronadjiZdravstevnogRadnika(String email) throws Exception {
		String xPathIzraz = String.format("/ZdravstveniRadnik[ct:Email = '%s']", email);
		String xml = this.pronadjiKorisnikaXml(XMLCollectionIdKonstante.COLLECTION_ID_ZDRAVSTVENI_RADNINK, xPathIzraz, 
				XMLNamespaceKonstante.NAMESPACE_ZDRAVSTVENI_RADNIK);
		if (xml != null) {
			return (ZdravstveniRadnik) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_ZDRAVSTVENI_RADNIK, 
				XSDPutanjeKonstante.XSD_ZDRAVSTVENI_RADNIK);
		}
		return null;
	}
	
	private String pronadjiKorisnikaXml(String collectionId, String xpath, String namespace) throws Exception {
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(collectionId, xpath, namespace);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String korisnik = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            korisnik = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return korisnik;
    }
}
