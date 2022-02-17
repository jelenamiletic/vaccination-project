<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:po="http:///www.ftn.uns.ac.rs/vakcinacija/potvrda"
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
        		<h1 id = "naslov">Potvrda o izvrsenoj vakcinaciji protiv COVID-19</h1>
        		<p style = "padding-top:10px">
                   Ime i prezime: <xsl:value-of select="//po:LicneInformacije/po:PunoIme/ct:Ime/text()"/> <xsl:value-of select="//po:LicneInformacije/po:PunoIme/ct:Prezime/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Datum rodjenja: <xsl:value-of select="//po:LicneInformacije/po:DatumRodjenja/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Pol: <xsl:value-of select="//po:LicneInformacije/po:Pol/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	JMBG: <xsl:value-of select="//po:LicneInformacije/po:JMBG/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Datum davanja i broj serije prve doze vakcine, serija: <xsl:value-of select="concat(' ', //po:InformacijeOVakcinama[1]/po:DatumDavanja/text(), //po:InformacijeOVakcinama[1]/po:Serija/text())"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Datum davanja i broj serije druge doze vakcine, serija: <xsl:value-of select="concat(' ', //po:InformacijeOVakcinama[2]/po:DatumDavanja/text(), //po:InformacijeOVakcinama[2]/po:Serija/text())"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Zdravstvena ustanova koja vakcinise: <xsl:value-of select="//po:ZdravstvenaUstanova/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Naziv vakcine: <xsl:value-of select="//po:VakcinaPrveDveDoze/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Datum izdavanja potvrde: <xsl:value-of select="//po:DatumIzdavanja/text()"/>
                </p>
                
                <xsl:variable name="QR" select="//po:QR/text()"/>
                <div style="width: 20vw; margin-right: 20vw; float:right">
                    <image src="data:image/jpeg;base64,{$QR}"/>
                </div>
        	</body>
        </html>
    </xsl:template>
</xsl:stylesheet>
