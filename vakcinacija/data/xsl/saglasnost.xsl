<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:sa="http:///www.ftn.uns.ac.rs/vakcinacija/saglasnost"
    xmlns:ct="http:///www.ftn.uns.ac.rs/vakcinacija/commonTypes"
    version="2.0">
    <xsl:template match="/">
        <html>
        	<head>
        		<style type="text/css">
        			body 
                    { 
                    	font-family: sans-serif;
                    }
        			#naslov {
        				font-size=24px;
        				font-weight:bold;
        				text-align:center;
        				margin-top: 10vh;
        			}
        			p 
                    {
                    	margin-left: 35vw;
                    }
                    ul li
                    {
                    	margin-left: 35vw;
                    }
                    table {
                    	border: 1px solid black;
                    	text-align:center;
                        font-family:serif;
                        width: 30%;
                        margin-left: 35vw;
                    }
                    th, td {
                    	border: 1px solid black;
                        text-align: center;
                        padding: 10px;
                    }
                    th {
                    	border: 1px solid black;
                        font-family: sans-serif;
                        font-weight="bold";
                    }
                    tr { border: 1px solid black; }
                    #potpis 
                    {
                    	border-top: 1px solid black;
                    	width: 10%;
                    	margin: 0px 0px 0px 54%;
                    	padding: 15px;
                    	text-align: center;
                    }
                </style>
        	</head>
        	<body>
        		<h1 id = "naslov">Saglasnost za sprovodjenje preporucene imunizacije</h1>
        		<p style = "padding-top:10px">
        		   <span style="font-weight:bold;">Drzavljantsvo</span>
                   <xsl:choose>
					 <xsl:when test="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:RepublikaSrbija">
					  	<span style = "padding-top:10px;">
		                	Republika Srbija | <xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:RepublikaSrbija/sa:JMBG/text()"/>
		                </span>
					 </xsl:when>
					 <xsl:when test="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/StranoDrzavljanstvo">
					  	<span style = "padding-top:10px;">
		                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:StranoDrzavljanstvo/sa:NazivDrzave/text()"/> | <xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Drzavljanstvo/sa:StranoDrzavljanstvo/sa:BrojPasosa/text()"/>
		                </span>
					 </xsl:when>
					</xsl:choose>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Prezime | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:PunoIme/ct:Ime/text()"/>
                	
                	<span style="font-weight:bold;"> Ime | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:PunoIme/ct:Prezime/text()"/>
                	
                	<span style="font-weight:bold;"> Ime roditelja | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:ImeRoditelja/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Pol | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Pol/text()"/>
                	
                	<span style="font-weight:bold;"> Datum rodjenja | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:DatumRodjenja/text()"/>
                	
                	<span style="font-weight:bold;"> Mesto rodjenja | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:MestoRodjenja/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Adresa | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Adresa/text()"/>
                	
                	<span style="font-weight:bold;"> Mesto/Naselje | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Mesto/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Opstina/Grad | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Opstina/text()"/>
                	
                	<span style="font-weight:bold;"> Tel. fiksni | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:BrojFiksnogTelefona/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Tel. mobilni | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:BrojMobilnogTelefona/text()"/>
                	
                	<span style="font-weight:bold;"> Email | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:Email/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Radni status : </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:RadniStatus/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Zanimanje zaposlenog : </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:LicneInformacije/sa:ZanimanjeZaposlenog/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Naziv ustanove socijalne zastite |</span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:UstanovaSocijalneZastite/sa:Naziv/text()"/>
                	
                	<span style="font-weight:bold;"> Mesto/Naselje | </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:UstanovaSocijalneZastite/sa:Opstina/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Naziv imunoloskog leka : </span>
                	<xsl:value-of select="//sa:PacijentSaglasnost/sa:Imunizacija/sa:NazivImunoloskogLeka/text()"/>
                </p>
                
                <p style = "padding-top:30px;">
	                Datum izdavanja: 
	                <u><xsl:value-of select="//sa:DatumPodnosenja/text()"/></u>
	                godine.	
                </p>
                <p id="potpis">Potpis</p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;">#####################################################################################################</span>
                </p>
                
                <h1 id = "naslov">Evidencija o vakcinaciji protiv COVID-19</h1>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Zdravstvena ustanova | </span>
                	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:ZdravstvenaUstanova/text()"/>
                	
                	<span style="font-weight:bold;"> Vakcinacijski punkt | </span>
                	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:VakcinacijskiPunkt/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Ime lekara | </span>
                	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:PunoIme/ct:Ime/text()"/>
                	
                	<span style="font-weight:bold;"> Prezime lekara | </span>
                	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:PunoIme/ct:Prezime/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	<span style="font-weight:bold;"> Broj telefona lekara : </span>
                	<xsl:value-of select="//sa:ZdravstveniRadnikSaglasnost/sa:LicneInformacijeLekara/sa:BrojTelefona/text()"/>
                </p>
       			
        	</body>
        </html>
    </xsl:template>
</xsl:stylesheet>
