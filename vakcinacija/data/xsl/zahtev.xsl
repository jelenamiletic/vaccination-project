<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:za="http:///www.ftn.uns.ac.rs/vakcinacija/zahtev"
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
        		<h1 id = "naslov">Zahtev za zeleni sertifikat COVID-19</h1>
                
                <p style = "padding-top:10px;">
                	JMBG
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:JMBG/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Ime
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:PunoIme/za:Ime/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Prezime
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:PunoIme/za:Prezime/text()"/>
                </p>
                
                
                <p style = "padding-top:10px;">
                	Datum rodjenja
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:DatumRodjenja/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Pol
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:Pol/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Broj Pasosa
                </p>
                
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:Podnosilac/za:BrojPasosa/text()"/>
                </p>
                
                <p style = "padding-top:10px;">
                	Razlog podnosenja
                </p>
                <p style = "padding-top:10px;">
                	<xsl:value-of select="//za:RazlogPodnosenja"/>
                </p>
              	
                 <p style = "padding-top:100px;">
	                Datum podnosenja: 
	                <u><xsl:value-of select="//za:DatumPodnosenja/text()"/></u>
	                godine.	
                </p>
                <p id="potpis">Potpis</p>
       			
        	</body>
        </html>
    </xsl:template>
</xsl:stylesheet>
