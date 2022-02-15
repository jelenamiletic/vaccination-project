<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:in="http:///www.ftn.uns.ac.rs/vakcinacija/interesovanje"
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
        		<h1 id = "naslov">Iskazivanje interesovanja za vakcinisanje protiv COVID-19</h1>
        		<p style = "padding-top:10px">
                   Gradjanin je: <xsl:value-of select="//in:LicneInformacije/in:Drzavljanstvo/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	JMBG:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:JMBG/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Ime:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:PunoIme/ct:Ime/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Prezime:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:PunoIme/ct:Prezime/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Adresa elektronske poste:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:AdresaElektronskePoste/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Broj Mobilnog telefona:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:BrojMobilnogTelefona/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Broj fiksnog telefona:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:LicneInformacije/in:BrojFiksnogTelefona/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Opstina primanja:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:OpstinaPrimanja/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Tip vakcina:
                </p>
                
                <p style = "padding-top:10px; padding-left:50px">
                	<xsl:value-of select="//in:Vakcina/text()"/>
                </p>
                
                <p style = "padding-top:10px; font-weight:bold;">
                	Davalac krvi:
                </p>
                <p style = "padding-top:10px;">
                	<xsl:choose>
					 <xsl:when test="//in:DavalacKrvi/text()='true'">
					  	<p style = "padding-top:10px; padding-left:50px">
		                	Da
		                </p>
					 </xsl:when>
					 <xsl:when test="//in:DavalacKrvi/text()='false'">
					  	<p style = "padding-top:10px; padding-left:50px">
		                	Ne
		                </p>
					 </xsl:when>
					</xsl:choose>
                </p>
              	
                 <p style = "padding-top:100px;">
	                Datum izdavanja: 
	                <u><xsl:value-of select="//in:DatumPodnosenja/text()"/></u>
	                godine.	
                </p>
                <p id="potpis">Potpis</p>
       			
        	</body>
        </html>
    </xsl:template>
</xsl:stylesheet>
