<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:sa="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost"
    xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
    version="2.0">
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="saglasnost-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="saglasnost-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Saglasnost za sprovodjenje preporucene imunizacije
                    </fo:block>
                    <fo:block font-size="13px" padding="10px">
                    		<fo:inline>
	                    		<fo:inline font-weight="bold">
	                    			Drzavljantsvo
	                    		</fo:inline>
			                   	<xsl:choose>
									 <xsl:when test="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:RepublikaSrbija">
									  	<fo:inline>
						                	Republika Srbija | <xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:RepublikaSrbija/sa:JMBG/text()"/>
						                </fo:inline>
									 </xsl:when>
									 <xsl:when test="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/StranoDrzavljanstvo">
									  	<fo:inline>
						                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:StranoDrzavljanstvo/sa:NazivDrzave/text()"/> | <xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:StranoDrzavljanstvo/sa:BrojPasosa/text()"/>
						                </fo:inline>
									 </xsl:when>
								</xsl:choose>
                            </fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> Ime | </fo:inline>
		                	<fo:inline><xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:PunoIme/ct:Ime/text()"/></fo:inline>
		                	
		                	<fo:inline font-weight="bold"> Prezime | </fo:inline>
		                	<fo:inline><xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:PunoIme/ct:Prezime/text()"/></fo:inline>
		                	
		                	<fo:inline font-weight="bold"> Ime roditelja | </fo:inline>
		                	<fo:inline><xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:ImeRoditelja/text()"/></fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		Pol | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Pol/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	Datum rodjenja | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:DatumRodjenja/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                		Mesto rodjenja | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:MestoRodjenja/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Adresa | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Adresa/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Mesto/Naselje | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Mesto/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Opstina/Grad | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Opstina/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Tel. fiksni | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:BrojFiksnogTelefona/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Tel. mobilni | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:BrojMobilnogTelefona/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Email | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Email/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                     <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Radni status : 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:RadniStatus/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Zanimanje zaposlenog : 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:ZanimanjeZaposlenog/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Naziv ustanove socijalne zastite |
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:UstanovaSocijalneZastite/sa:Naziv/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Mesto/Naselje | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:UstanovaSocijalneZastite/sa:Opstina/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Naziv imunoloskog leka : 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:PacijentSaglasnost/sa:Imunizacija/sa:NazivImunoloskogLeka/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block-container>
       						<fo:block font-size="13px">
	                            <fo:inline>
	                                Datum izdavanja: 
	                                <fo:inline text-decoration="underline">
	                                   <xsl:value-of select="format-dateTime(//sa:DatumPodnosenja/text(), '[Y0001]-[M01]-[D01] [H01]:[m01]')"/>
	                                   
	                                </fo:inline>
	                                godine
	                            </fo:inline>
                        	</fo:block>
	                        <fo:block-container width="40%" left="60%" top="0in" position="absolute" margin="2em">
	                        	<fo:block text-align = "center">
	                        		<fo:leader leader-pattern="rule" leader-length.minimum="2.5in" leader-length.optimum="2in" leader-length.maximum="3in"/>
	                        		<fo:block>Potpis</fo:block>
	                        	</fo:block>
	                        </fo:block-container>
                    	</fo:block-container>
                    	
                    <fo:block font-size="13px" padding-top="20px">
                        	<fo:inline font-weight="bold"> 
                        		 #############################################################
                        	</fo:inline>
                    </fo:block>
                    
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold" padding="10px">
                        Evidencija o vakcinaciji protiv COVID-19
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Zdravstvena ustanova | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:ZdravstvenaUstanova/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Vakcinacijski punkt | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:VakcinacijskiPunkt/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		 Ime lekara | 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:PunoIme/ct:Ime/text()"/>
		                	</fo:inline>
		                	
		                	<fo:inline font-weight="bold"> 
		                	 	 Prezime lekara | 
		                	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:PunoIme/ct:Prezime/text()"/>
		                	</fo:inline>
                    </fo:block>
                    
                    <fo:block font-size="13px" padding="10px">
                        	<fo:inline font-weight="bold"> 
                        		  Broj telefona lekara : 
                        	</fo:inline>
		                	<fo:inline>
		                		<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:BrojTelefona/text()"/>
		                	</fo:inline>
                    </fo:block>
                    <fo:block font-size="10px">
	                    <fo:table text-align="center" font-family="serif" border="1px solid black" margin="5px auto 5px auto">
	                    	<fo:table-column column-width="20%"/>
	                        <fo:table-column column-width="18%"/>
	                        <fo:table-column column-width="9%"/>
	                        <fo:table-column column-width="11%"/>
	                        <fo:table-column column-width="10%"/>
	                        <fo:table-column column-width="13%"/>
	                        <fo:table-column column-width="15%"/>
	                        <fo:table-column column-width="8%"/>
	                        <fo:table-body>
	                            	<fo:table-row border="1px solid black">
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Naziv vakcine</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Datum davanja vakcine (V1 i V2)</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="10px" border="1px solid black">
	                                        <fo:block>Nacin davanja vakcine</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Ekstremitet</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Serija vakcine (lot)</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Proizvodjac</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Nezeljena reakcija</fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell font-family="sans-serif" padding="2px" border="1px solid black">
	                                        <fo:block>Potpis lekara</fo:block>
	                                    </fo:table-cell>
	                                </fo:table-row>
	                                <fo:table-row border="1px solid black">
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:NazivVakcine/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="format-dateTime(//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:DatumDavanjaVakcine/text(), '[Y0001]-[M01]-[D01] [H01]:[m01]')"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            IM
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:Ekstremitet/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:SerijaVakcine/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:Proizvodjac/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[1]/sa:NezeljanaReakcija/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="10px" border="1px solid black">
	                                        <fo:block>
	                                            
	                                        </fo:block>
	                                    </fo:table-cell>
	                                </fo:table-row>
	                                <fo:table-row border="1px solid black">
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:NazivVakcine/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:DatumDavanjaVakcine/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            IM
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:Ekstremitet/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:SerijaVakcine/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:Proizvodjac/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:VakcineInfo[2]/sa:NezeljanaReakcija/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                    <fo:table-cell padding="2px" border="1px solid black">
	                                        <fo:block>
	                                            
	                                        </fo:block>
	                                    </fo:table-cell>
	                                </fo:table-row>
	                                <fo:table-row border="1px solid black">
	                                    <fo:table-cell padding="2px" border="1px solid black" number-columns-spanned = "8">
	                                        <fo:block text-align = "left">
	                                            Privremene kontraindikacije
	                                        </fo:block>
	                                        <fo:block text-align = "left">
	                                            (Datum utvrdjivanja i dijagnoza)
	                                        </fo:block>
	                                        <fo:block text-align = "right">
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:PrivremeneKontraindikacije/sa:DatumUtvrdjivanja/text()"/>
	                                        </fo:block>
	                                        <fo:block text-align = "right">
	                                            <xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:PrivremeneKontraindikacije/sa:Dijagnoza/text()"/>
	                                        </fo:block>
	                                    </fo:table-cell>
	                                </fo:table-row>
	                                <fo:table-row border="1px solid black">
	                                	<fo:table-cell padding="2px" border="1px solid black" number-columns-spanned = "8">
	                                		<fo:block text-align = "left">
	                                     		Odluka komisije za trajne kontraindikacije (ako postoji upistati Da)
		                                    </fo:block>
		                                    <fo:block text-align = "right">
		                                     	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:Obrazac/sa:TrajneKontraindikacije/text()"/>
		                                    </fo:block>
	                                	</fo:table-cell>
	                               </fo:table-row>
	                            </fo:table-body>
	                		</fo:table>
                		</fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
