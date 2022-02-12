<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:iz="http:///www.ftn.uns.ac.rs/vakcinacija/izvestaj"
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
        		<h1 id = "naslov">Izvestaj o imunizaciji</h1>
        		<p style = "padding-top:10px">Izvestaj se odnosi na period od 
                    <b><xsl:value-of select="//iz:PeriodIzvestaja/iz:OdDatum/text()"/></b> do
                    <b><xsl:value-of select="//iz:PeriodIzvestaja/iz:DoDatum/text()"/></b>.
                </p>
                <p style = "padding-top:20px;">U napomenutom vremenskom intervalu je: 
                    <ul list-style-type = "circle">
  						<li>Podneto <b><xsl:value-of select="//iz:BrojPodnetihDokumenata/text()"/></b> dokumenata o interesovanju za imunizaciju;
  						</li>
  						<li>Primljeno <b><xsl:value-of select="//iz:ZahteviZaDigitalniSertifikat/iz:BrojPrimljenih/text()"/></b>
  						 zahteva za digitalni zeleni sertifikat, od kojih je <b><xsl:value-of select="//iz:ZahteviZaDigitalniSertifikat/iz:BrojIzdatih/text()"/></b>
  						  izdato.
  						 </li>
					</ul>
                </p>
                <p style = "padding-top:10px">
                	Dato je <b><xsl:value-of select="//iz:UkupanBrojDatihDoza/text()"/></b>
                	 doza vakcine protiv COVID-19 virusa u sledecoj kolicini:
                </p>
                <table>
                    <tr>
                        <th>Redni broj doze</th>
                        <th>Broj datih doza</th>
                    </tr>
                    <tr>
                        <td><b><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[1]/iz:RedniBroj/text()"/></b></td>
                        <td><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[1]/iz:BrojDatihDoza/text()"/></td>
                    </tr>
                    <tr>
                        <td><b><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[2]/iz:RedniBroj/text()"/></b></td>
                        <td><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[2]/iz:BrojDatihDoza/text()"/></td>
                    </tr>
                    <tr>
                        <td><b><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[3]/iz:RedniBroj/text()"/></b></td>
                        <td><xsl:value-of select="//iz:KolicnaDozaPoRednomBroju[3]/iz:BrojDatihDoza/text()"/></td>
                    </tr>
                </table>
                <p style = "padding-top:43px;">
                	Raspodela po proizvodjacima je:
                	<ul list-style-type = "circle">
  						<li>
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[1]/iz:Proizvodjac/text()"/></b> - 
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[1]/iz:BrojDatihDoza/text()"/></b> doza;
  						</li>
  						<li>
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[2]/iz:Proizvodjac/text()"/></b> - 
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[2]/iz:BrojDatihDoza/text()"/></b> doza;
  						</li>
  						<li>
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[3]/iz:Proizvodjac/text()"/></b> - 
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[3]/iz:BrojDatihDoza/text()"/></b> doza;
  						</li>
  						<li>
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[4]/iz:Proizvodjac/text()"/></b> - 
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[4]/iz:BrojDatihDoza/text()"/></b> doza;
  						</li>
  						<li>
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[5]/iz:Proizvodjac/text()"/></b> - 
  							<b><xsl:value-of select="//iz:RaspodelaDozaPoProizvodjacu[5]/iz:BrojDatihDoza/text()"/></b> doza.
  						</li>
					</ul>
                </p>
                <p style = "padding-top:100px;">
	                Datum izdavanja: 
	                <u><xsl:value-of select="//iz:Izvestaj/@DatumIzdavanja"/></u>
	                godine.	
                </p>
                <p id="potpis">Potpis</p>
        	</body>
        </html>
    </xsl:template>
</xsl:stylesheet>
