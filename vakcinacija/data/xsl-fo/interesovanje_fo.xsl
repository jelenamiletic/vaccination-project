<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:in="http:///www.ftn.uns.ac.rs/vakcinacija/interesovanje"
    xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
    version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="interesovanje-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="interesovanje-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Iskazivanje interesovanja za vakcinisanje protiv COVID-19
                    </fo:block>
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	 	Gradjanin je:
                        	 </fo:inline>
                            <fo:inline>
                                <xsl:value-of select="//in:LicneInformacije/in:Drzavljanstvo/text()"/>
                            </fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    JMBG:
                        	 </fo:inline>
                            <fo:block  padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:JMBG/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Ime:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:PunoIme/ct:Ime/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Prezime:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:PunoIme/ct:Prezime/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Adresa elektronske poste:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:AdresaElektronskePoste/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Broj mobilnog telefona:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:BrojMobilnogTelefona/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Broj fiksnog telefona:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:BrojFiksnogTelefona/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Broj fiksnog telefona:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:LicneInformacije/in:BrojFiksnogTelefona/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Opstina primanja:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:OpstinaPrimanja/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Tip vakcina:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:value-of select="//in:Vakcina/text()"/>
                            </fo:block>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	 <fo:inline font-weight="bold">
                        	    Davalac krvi:
                        	 </fo:inline>
                            <fo:block padding-left="50px">
                                <xsl:choose>
									 <xsl:when test="//in:DavalacKrvi/text()='true'">
									  	<p style = "padding-top:10px;">
						                	Da
						                </p>
									 </xsl:when>
									 <xsl:when test="//in:DavalacKrvi/text()='false'">
									  	<p style = "padding-top:10px;">
						                	Ne
						                </p>
									 </xsl:when>
								</xsl:choose>
                            </fo:block>
                    </fo:block>
                    
       					<fo:block-container>
       						<fo:block font-size="13px">
	                            <fo:inline>
	                                Datum izdavanja: 
	                                <fo:inline text-decoration="underline">
	                                   <xsl:value-of select="//in:DatumPodnosenja/text()"/>
	                                </fo:inline>
	                                godine
	                            </fo:inline>
                        	</fo:block>
	                        <fo:block-container width="40%" left="60%" top="0in" position="absolute">
	                        	<fo:block text-align = "center">
	                        		<fo:leader leader-pattern="rule" leader-length.minimum="2.5in" leader-length.optimum="2in" leader-length.maximum="3in"/>
	                        		<fo:block>Potpis</fo:block>
	                        	</fo:block>
	                        </fo:block-container>
                    	</fo:block-container>
                    	
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
