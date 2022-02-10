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
import com.xml.sluzbenik.model.vakcina.Vakcina;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.UnmarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XMLCollectionIdKonstante;
import com.xml.sluzbenik.utils.XMLNamespaceKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@Repository
public class VakcinaRepository {

	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveVakcinaXml(String xml, String naziv) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_VAKCINE, naziv.toLowerCase(), xml);
    }
	
	public void saveVakcinaObjekat(Vakcina vakcina) throws Exception {
		String xml = marshallerService.marshall(vakcina, ContextPutanjeKonstante.CONTEXT_PUTANJA_VAKCINA, 
				XSDPutanjeKonstante.XSD_VAKCINA);
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_VAKCINE, vakcina.getNaziv().toString(), xml);
	}
	
	public List<Vakcina> pronadjiSve() throws Exception {
		String xPathIzraz = "//Vakcina";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_VAKCINE, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_VAKCINA);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Vakcina> vakcine = new ArrayList<Vakcina>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            vakcine.add((Vakcina) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_VAKCINA, XSDPutanjeKonstante.XSD_VAKCINA));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return vakcine;
	}

	public String pronadjiVakcinuXml(String naziv) throws Exception {
        String xPathIzraz = String.format("/Vakcina[Naziv = '%s']", naziv);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_VAKCINE, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_VAKCINA);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        String vakcina = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            vakcina = res.getContent().toString();
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return vakcina;
    }
	
	public Vakcina pronadjiVakcinu(String naziv) throws Exception {
		String xml = this.pronadjiVakcinuXml(naziv);
		if (xml != null) {
			return (Vakcina) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_VAKCINA, 
				XSDPutanjeKonstante.XSD_VAKCINA);
		}
		return null;
	}
}
