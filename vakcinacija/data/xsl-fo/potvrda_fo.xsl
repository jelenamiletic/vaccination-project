<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:po="http:///www.ftn.uns.ac.rs/vakcinacija/potvrda"
    xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
    version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="potvrda-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="potvrda-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Potvrda o izvrsenoj vakcinaciji protiv COVID-19
                    </fo:block>
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	 	Ime i prezime:
                        	 </fo:inline>
                            <fo:inline>
                                <xsl:value-of select="//po:LicneInformacije/po:PunoIme/ct:Ime/text()"/>
                                <xsl:value-of select="//po:LicneInformacije/po:PunoIme/ct:Prezime/text()"/>
                            </fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	 	Datum rodjenja:
                        	 </fo:inline>
                            <fo:inline>
                                <xsl:value-of select="//po:LicneInformacije/po:DatumRodjenja/text()"/>
                            </fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	 	Pol:
                        	 </fo:inline>
                            <fo:inline>
                                <xsl:value-of select="//po:LicneInformacije/po:Pol/text()"/>
                            </fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    JMBG:
                        	 </fo:inline>
                            <fo:block>
                                <xsl:value-of select="//po:LicneInformacije/po:JMBG/text()"/>
                            </fo:block>
                    </fo:block>    	
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    Datum davanja i broj serije prve doze vakcine:
                        	    ,serija:
                        	 </fo:inline>
                            <fo:block>
                                <xsl:value-of select="//po:InformacijeOVakcinama/po:DatumDavanja/text()"/>
                                <xsl:value-of select="//po:InformacijeOVakcinama/po:Serija/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    Datum davanja i broj serije druge doze vakcine:
                        	 </fo:inline>
                            <fo:block>
                                
                            </fo:block>
                    </fo:block> 
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    Zdravstvena ustanova koja vakcinise:
                        	 </fo:inline>
                            <fo:block>
                                <xsl:value-of select="//po:ZdravstvenaUstanova/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    Naziv vakcine:
                        	 </fo:inline>
                            <fo:block>
                                <xsl:value-of select="//po:VakcinaPrveDveDoze/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline>
                        	    Datum izdavanja potvrde:
                        	 </fo:inline>
                            <fo:block>
                                <xsl:value-of select="//po:DatumIzdavanja/text()"/>
                            </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
