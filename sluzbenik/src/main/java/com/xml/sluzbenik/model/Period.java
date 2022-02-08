package com.xml.sluzbenik.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "OdDatum",
        "DoDatum"
})
@XmlRootElement(name = "PeriodIzvestaja")
public class Period {

	@XmlElement(required = true)
	protected XMLGregorianCalendar OdDatum;
	
	@XmlElement(required = true)
	protected XMLGregorianCalendar DoDatum;

	public XMLGregorianCalendar getOdDatum() {
		return OdDatum;
	}

	public void setOdDatum(XMLGregorianCalendar odDatum) {
		OdDatum = odDatum;
	}

	public XMLGregorianCalendar getDoDatum() {
		return DoDatum;
	}

	public void setDoDatum(XMLGregorianCalendar doDatum) {
		DoDatum = doDatum;
	}
}
