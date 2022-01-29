package com.xml.sluzbenik.model.izvestaj;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
    }
	
	public Izvestaj createIzvestaj() {
		return new Izvestaj();
	}
	
	public Izvestaj.BrojPodnetihDokumenata createBrojPodnetihDokumenata() {
		return new Izvestaj.BrojPodnetihDokumenata();
	}
	
	public Izvestaj.KolicnaDozaPoRednomBroju createKolicinaDozaPoRednomBroju() {
		return new Izvestaj.KolicnaDozaPoRednomBroju();
	}
	
	public Izvestaj.PeriodIzvestaja createPeriodIzvestaja() {
		return new Izvestaj.PeriodIzvestaja();
	}
	
	public Izvestaj.RaspodelaDozaPoProizvodjacu createRaspodelaDozaPoProizvodjacu() {
		return new Izvestaj.RaspodelaDozaPoProizvodjacu();
	}
	
	public Izvestaj.ZahteviZaDigitalniSertifikat createZahteviZaDigitalniSertifikat() {
		return new Izvestaj.ZahteviZaDigitalniSertifikat();
	}
	
	public Izvestaj.ZahteviZaDigitalniSertifikat.BrojIzdatih createBrojIzdatih() {
		return new Izvestaj.ZahteviZaDigitalniSertifikat.BrojIzdatih();
	}
	
	public Izvestaj.ZahteviZaDigitalniSertifikat.BrojPrimljenih createBrojPrimljenih() {
		return new Izvestaj.ZahteviZaDigitalniSertifikat.BrojPrimljenih();
	}
	
	public ListaIzvestaja createListaIzvestaja() {
		return new ListaIzvestaja();
	}
}
