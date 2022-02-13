package com.xml.vakcinacija.model.termin;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Termin"
})
@XmlRootElement(name = "ListaTermina")
public class ListaTermina {
    @XmlElement(name = "Termin")
    protected List<Termin> Termin;

    public List<Termin> getTermin() {
        if (Termin == null) {
            Termin = new ArrayList<Termin>();
        }
        return this.Termin;
    }

    public void setTermin(List<Termin> Termin) {
        this.Termin = Termin;
    }

}
