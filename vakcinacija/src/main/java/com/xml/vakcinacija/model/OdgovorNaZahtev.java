package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "JMBG",
        "RazlogOdbijanja"
})
@XmlRootElement(name = "OdgovorNaZahtev")
public class OdgovorNaZahtev {

	@XmlElement(required = true)
	protected String JMBG;
	
	@XmlElement
	protected String RazlogOdbijanja;

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public String getRazlogOdbijanja() {
		return RazlogOdbijanja;
	}

	public void setRazlogOdbijanja(String razlogOdbijanja) {
		RazlogOdbijanja = razlogOdbijanja;
	}
}
