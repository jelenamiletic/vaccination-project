package com.xml.vakcinacija.controller;

import com.xml.vakcinacija.model.sertifikat.ListaSertifikata;
import com.xml.vakcinacija.model.sertifikat.Sertifikat;
import com.xml.vakcinacija.service.SertifikatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/sertifikat")
public class SertifikatController {

    @Autowired
    SertifikatService sertifikatService;

    @PostMapping(value = "/dodajNoviSertifikat", consumes = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_SLUZBENIK')")
    public void dodajNoviSertifikat(@RequestBody String sertifikatXML) throws Exception {
        sertifikatService.dodajNoviSertifikat(sertifikatXML);
    }

    @GetMapping(value = "/pronadjiSve", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_SLUZBENIK')")
    public ResponseEntity<ListaSertifikata> pronadjiSve() throws Exception {
        ListaSertifikata lista = new ListaSertifikata();
        lista.setSertifikat(sertifikatService.pronadjiSve());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/pronadjiSertifikatPoJmbg/{jmbg}", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
    public ResponseEntity<Sertifikat> pronadjiSertifikataPoJmbg(@PathVariable String jmbg) throws Exception {
        return new ResponseEntity<>(sertifikatService.pronadjiSertifikatPoJmbg(jmbg), HttpStatus.OK);
    }

    @GetMapping(value = "/nabaviMetaPodatkeJSONPoJmbg/{jmbg}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_GRADJANIN', 'ROLE_SLUZBENIK')")
    public ResponseEntity<InputStreamResource> nabaviMetaPodatkeJSONPoJmbg(@PathVariable String jmbg) throws IOException {
         return new ResponseEntity<>(new InputStreamResource(sertifikatService.nabaviMetaPodatkeJSONPoJmbg(jmbg)), HttpStatus.OK);
    }
}
