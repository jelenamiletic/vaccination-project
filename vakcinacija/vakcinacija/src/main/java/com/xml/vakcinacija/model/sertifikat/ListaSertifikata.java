package com.xml.vakcinacija.model.sertifikat;

import com.xml.vakcinacija.model.zahtev.Zahtev;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Sertifikat"
})
@XmlRootElement(name = "ListaSertifikata")
public class ListaSertifikata {
    @XmlElement(name = "Sertifikat")
    protected List<com.xml.vakcinacija.model.sertifikat.Sertifikat> Sertifikat;

    public List<Sertifikat> getSertifikat() {
        if (Sertifikat == null) {
            Sertifikat = new ArrayList<Sertifikat>();
        }
        return this.Sertifikat;
    }

    public void setSertifikat(List<Sertifikat> sertifikat) {
        this.Sertifikat = Sertifikat;
    }

}
