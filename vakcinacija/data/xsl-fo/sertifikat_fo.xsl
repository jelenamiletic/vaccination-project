<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:se="http:///www.ftn.uns.ac.rs/vakcinacija/sertifikat"
    xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
    version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="sertifikat-page">
                    <fo:region-body 
                    	page-height="30cm"
                        page-width="20cm"
                        margin="0.2cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="sertifikat-page">
                <fo:flow flow-name="xsl-region-body">
                	<fo:block-container>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" font-weight="bold">
	                    	DIGITALNI ZELENI SERTIFIKAT
	                    </fo:block>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" padding="1px">
	                        Potvrda o izvrsenoj vakcinaciji protiv
	                    </fo:block>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" padding="1px">
	                        COVID-19 i rezultatima testiranja
	                    </fo:block>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" font-weight="bold" padding="1px">
	                        DIGITAL GREEN CERTIFICATE
	                    </fo:block>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" padding="1px">
	                        Certificate of vaccination against COVID-19
	                    </fo:block>
	                    <fo:block text-align="center" font-family="sans-serif" font-size="14px" padding="1px">
	                        and test results
	                    </fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding-top = "64px">
	                    	Broj sertifikata / 
	                    	<fo:inline>
	                    		<xsl:value-of select="//se:BrojSertifikata/text()"/>
	                    	</fo:inline>
	                     </fo:block>
	                    <fo:block font-size="11px" font-weight="bold">Certificate ID:</fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding="10px">
	                    	Ime i prezime / Name and surname: 
	                    	<fo:inline font-size="11px" font-weight = "normal">
	                    		<xsl:value-of select="concat(' ', //se:LicneInformacije/se:PunoIme/ct:Ime/text(), ' ', //se:LicneInformacije/se:PunoIme/ct:Prezime/text())"/>
	                    	</fo:inline>
	                    </fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding="10px">
	                    	Pol / Gender: 
	                    	<fo:inline font-size="11px">
	                    		<xsl:value-of select="//se:LicneInformacije/se:Pol/text()"/> 
	                    	</fo:inline>
	                    </fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding="10px">
	                    	Datum rodjenja / Date of birth: 
	                    	<fo:inline font-size="11px">
	                    		<xsl:value-of select="//se:LicneInformacije/se:DatumRodjenja/text()"/> 
	                    	</fo:inline>
	                    </fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding="10px">
	                    	JMBG / Personal No. / EBS: 
	                    	<fo:inline font-weight = "normal" font-size="11px">
	                    		<xsl:value-of select="//se:LicneInformacije/se:JMBG/text()"/> 
	                    	</fo:inline>
	                    </fo:block>
	                    <fo:block font-size="11px" font-weight="bold" padding="10px">
	                    	Broj pasosa / Passport No: 
	                    	<fo:inline font-size="11px">
	                    		<xsl:value-of select="//se:LicneInformacije/se:BrojPasosa/text()"/> 
	                    	</fo:inline>
	                    </fo:block>
	                    <fo:block font-family="sans-serif" font-size="11px" font-weight="bold">
	                     	Datum i vreme izdavanja sertifikata /
	                    </fo:block>
	                    <fo:block font-family="sans-serif" font-size="11px" font-weight="bold">
	                        Certificate issuing date and time:
	                        <fo:inline font-weight="normal">
	                            <xsl:value-of
	                                    select="//se:DatumVremeIzdavanja/text()"/>
	                        </fo:inline>
	                    </fo:block>
	                    <fo:block><fo:leader leader-pattern="rule" leader-length.minimum="8in" leader-length.optimum="8in" leader-length.maximum="8in"/></fo:block>
	                    <fo:block font-size="14px" font-weight="bold" text-align="center" padding-top="2px">
	                    		Vakcinacija / Vaccination 
	                     	</fo:block>
	                    <fo:block-container margin-top="10px" font-family="sans-serif">
	                     	<fo:block-container width="50%" left="0in" top="0in" position="absolute">
	                     		<fo:block font-size="11px" font-weight="bold" padding-top = "5px">
	                    		Doza / Dose 1/2:
	                     		</fo:block>
			                    <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Tip / Type:
			                    	<fo:block font-size="11px"  padding="5px">
			                    		<xsl:value-of select="//se:Vakcinacija[1]/se:TipVakcine/text()"/> 
			                    	</fo:block>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Proizvodjac i serija / Manufacturer and batch number:
			                    	<fo:block font-size="11px"  padding="5px">
			                    		<fo:inline>
			                    			<xsl:value-of select="//se:Vakcinacija[1]/se:Proizvodjac/text()"/>,
			                    			 <xsl:value-of select="//se:Vakcinacija[1]/se:Serija/text()"/>
			                    		</fo:inline>
			                    	</fo:block>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Datum / Date:
			                    	<fo:inline font-size="11px">
			                    		<xsl:value-of select="//se:Vakcinacija[1]/se:DatumDavanja/text()"/> 
			                    	</fo:inline>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Zdravstvena ustanova / Health care institution:
			                    	<fo:block font-size="11px">
			                    		<xsl:value-of select="//se:Vakcinacija[1]/se:ZdravstvenaUstanova/text()"/> 
			                    	</fo:block>
			                     </fo:block>
	                     	</fo:block-container>
		                     <fo:block-container width="50%" left="50%" top="0in" position="absolute">
		                     	<fo:block font-size="11px" font-weight="bold" padding-top = "5px">
		                    		Doza / Dose 2/2:
		                     	</fo:block>
			                    <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Tip / Type:
			                    	<fo:block font-size="11px"  padding="5px">
			                    		<xsl:value-of select="//se:Vakcinacija[2]/se:TipVakcine/text()"/> 
			                    	</fo:block>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Proizvodjac i serija / Manufacturer and batch number:
			                    	<fo:block font-size="11px"  padding="5px">
			                    		<fo:inline>
			                    			<xsl:value-of select="//se:Vakcinacija[2]/se:Proizvodjac/text()"/>,
			                    			 <xsl:value-of select="//se:Vakcinacija[2]/se:Serija/text()"/>
			                    		</fo:inline>
			                    	</fo:block>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Datum / Date:
			                    	<fo:inline font-size="11px">
			                    		<xsl:value-of select="//se:Vakcinacija[2]/se:DatumDavanja/text()"/> 
			                    	</fo:inline>
			                     </fo:block>
			                     <fo:block font-size="11px" font-weight="bold" padding-top = "5px">
			                    	Zdravstvena ustanova / Health care institution:
			                    	<fo:block font-size="11px">
			                    		<xsl:value-of select="//se:Vakcinacija[2]/se:ZdravstvenaUstanova/text()"/> 
			                    	</fo:block>
			                     </fo:block>
		                     </fo:block-container>
	                     </fo:block-container> 
                    	<fo:block font-size="10px" font-weight="bold" padding-top = "65px">
                        <fo:table font-family="serif" margin="173px auto 2px auto" border-bottom="1px solid #e7e9eb">
                            <fo:table-column column-width="33.3%" border="1px solid black"/>
                            <fo:table-column column-width="33.3%" border="1px solid black"/>
                            <fo:table-column column-width="33.3%" border="1px solid black"/>
                            <fo:table-body>
	                        	<fo:table-row border-bottom="1px solid darkgrey" font-size="12px">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>SARS-Co-2 RT Real-time PCR</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>SARS-CoV-2 Ag-RDT (Antigen Rapid Detection test)</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>SARS-CoV-2 RBD S-Protein Immunoglobulin G (IgG) test</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Vrsta uzorka / Sample type:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Vrsta uzorka / Sample type:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Vrsta uzorka / Sample type:</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="bold">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Proizvodjac testa / Test manufacturer</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Proizvodjac testa / Test manufacturer</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Proizvodjac testa / Test manufacturer</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal">
                            <fo:table-cell font-family="sans-serif" padding="2px">
                                <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="bold">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme uzorkovanja / Date and time of sampling</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme uzorkovanja / Date and time of sampling</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme uzorkovanja / Date and time of sampling</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="bold">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme izdavanja rezultata / Date and time of result</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme izdavanja rezultata / Date and time of result</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Datum i vreme izdavanja rezultata / Date and time of result</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal" border-bottom="1px solid black">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="bold" background-color="#e7e9eb">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Rezultat / Result:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Rezultat / Result:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Rezultat / Result:</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal" background-color="#e7e9eb">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="bold" background-color="#e7e9eb">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Laboratorija / Laboratoty:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Laboratorija / Laboratoty:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>Laboratorija / Laboratoty:</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row font-weight="normal" background-color="#e7e9eb">
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
                                <fo:table-cell font-family="sans-serif" padding="2px">
                                    <fo:block>N/A</fo:block>
                                </fo:table-cell>
	                        </fo:table-row>
	                     </fo:table-body>
                       </fo:table>
	                </fo:block>
                 </fo:block-container>
               </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
