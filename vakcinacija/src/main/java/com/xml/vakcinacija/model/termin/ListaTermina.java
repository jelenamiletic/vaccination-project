package com.xml.vakcinacija.model.termin;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "termin"
})
@XmlRootElement(name = "ListaTermina")
public class ListaTermina {
    @XmlElement(name = "termin")
    protected List<Termin> termin;

    public List<Termin> getTermin() {
        if (termin == null) {
            termin = new ArrayList<Termin>();
        }
        return this.termin;
    }

    public void setTermin(List<Termin> Termin) {
        this.termin = Termin;
    }

}
