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
import com.xml.vakcinacija.model.potvrda.Potvrda;
import com.xml.vakcinacija.service.MarshallerService;
import com.xml.vakcinacija.service.UnmarshallerService;
import com.xml.vakcinacija.utils.ContextPutanjeKonstante;
import com.xml.vakcinacija.utils.XMLCollectionIdKonstante;
import com.xml.vakcinacija.utils.XMLNamespaceKonstante;
import com.xml.vakcinacija.utils.XSDPutanjeKonstante;

@Repository
public class PotvrdaRepository {

	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void savePotvrdaXml(String xml, String jmbg, int brojDoze) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_POTVRDA, jmbg + "_" + brojDoze, xml);
    }
	
	public void savePotvrdaObjekat(Potvrda potvrda) throws Exception {
		String xml = marshallerService.marshall(potvrda, ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, 
				XSDPutanjeKonstante.XSD_POTVRDA);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_POTVRDA, potvrda.getLicneInformacije().getJMBG().getValue() + "_" + 
				potvrda.getInformacijeOVakcinama().get(potvrda.getInformacijeOVakcinama().size()-1).getBrojDoze(), xml);
	}
	
	public List<Potvrda> pronadjiSve() throws Exception {
		String xPathIzraz = "//Potvrda";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_POTVRDA, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_POTVRDA);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Potvrda> listaPotvrda = new ArrayList<Potvrda>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaPotvrda.add((Potvrda) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, XSDPutanjeKonstante.XSD_POTVRDA));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaPotvrda;
	}
	
	public String pronadjiPotvrdaXmlPoJmbg(String jmbg, int brojDoze) throws Exception {
        String xPathIzraz = String.format("/Potvrda[LicneInformacije/JMBG = '%s'and count(InformacijeOVakcinama/BrojDoze) = %d]" , jmbg, brojDoze);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_POTVRDA, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_POTVRDA);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String potvrda = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            potvrda = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return potvrda;
    }
	
	public Potvrda pronadjiPotvrdaPoJmbg(String jmbg, int brojDoze) throws Exception {
		String xml = this.pronadjiPotvrdaXmlPoJmbg(jmbg, brojDoze);
		if (xml != null) {
			return (Potvrda) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_POTVRDA, 
				XSDPutanjeKonstante.XSD_POTVRDA);
		}
		return null;
	}
}
