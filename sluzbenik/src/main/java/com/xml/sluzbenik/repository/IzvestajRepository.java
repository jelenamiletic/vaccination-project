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
import com.xml.sluzbenik.model.izvestaj.Izvestaj;
import com.xml.sluzbenik.service.MarshallerService;
import com.xml.sluzbenik.service.UnmarshallerService;
import com.xml.sluzbenik.utils.ContextPutanjeKonstante;
import com.xml.sluzbenik.utils.XMLCollectionIdKonstante;
import com.xml.sluzbenik.utils.XMLNamespaceKonstante;
import com.xml.sluzbenik.utils.XSDPutanjeKonstante;

@Repository
public class IzvestajRepository {

	@Autowired
	private MarshallerService marshallerService;
	
	@Autowired
	private UnmarshallerService unmarshallerService;
	
	public void saveIzvestajXml(String xml, String odDoDatumi) throws Exception {
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_IZVESTAJ, odDoDatumi, xml);
    }
	
	public void saveIzvestajObjekat(Izvestaj izvestaj) throws Exception {
		String xml = marshallerService.marshall(izvestaj, ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, 
				XSDPutanjeKonstante.XSD_IZVESTAJ);
		String odDoDatumi = izvestaj.getPeriodIzvestaja().getOdDatum() + "_" + izvestaj.getPeriodIzvestaja().getDoDatum();
		ExistStore.save(XMLCollectionIdKonstante.COLLECTION_ID_IZVESTAJ, odDoDatumi, xml);
	}
	
	public List<Izvestaj> pronadjiSve() throws Exception {
		String xPathIzraz = "//Izvestaj";
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_IZVESTAJ, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_IZVESTAJ);
        if (rezultat == null)
            return null;

        ResourceIterator i = rezultat.getIterator();
        XMLResource res = null;
        List<Izvestaj> listaIzvestaja = new ArrayList<Izvestaj>();

        while (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            listaIzvestaja.add((Izvestaj) unmarshallerService.unmarshal(res.getContent().toString(), 
            		ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, XSDPutanjeKonstante.XSD_IZVESTAJ));
        }

        if (res != null) {
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }
        }

        return listaIzvestaja;
	}
	
	public String pronadjiIzvestajXml(String odDatum, String doDatum) throws Exception {
        String xPathIzraz = String.format("/Izvestaj[PeriodIzvestaja/OdDatum = '%1$s' and PeriodIzvestaja/DoDatum = '%2$s']", odDatum, doDatum);
        ResourceSet rezultat = ExistRetrieve.izvrsiXPathIzraz(XMLCollectionIdKonstante.COLLECTION_ID_IZVESTAJ, 
        		xPathIzraz, XMLNamespaceKonstante.NAMESPACE_IZVESTAJ);
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
	
	public Izvestaj pronadjiIzvestaj(String odDatum, String doDatum) throws Exception {
		String xml = this.pronadjiIzvestajXml(odDatum, doDatum);
		if (xml != null) {
			return (Izvestaj) unmarshallerService.unmarshal(xml, 
				ContextPutanjeKonstante.CONTEXT_PUTANJA_IZVESTAJ, 
				XSDPutanjeKonstante.XSD_IZVESTAJ);
		}
		return null;
	}
}
