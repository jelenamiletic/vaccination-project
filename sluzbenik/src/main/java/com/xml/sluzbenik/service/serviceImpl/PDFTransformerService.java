package com.xml.sluzbenik.service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;

import com.xml.sluzbenik.utils.XSLFOKonstante;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class PDFTransformerService {
	
	public ByteArrayInputStream generatePDF(String xmlFile, String xslFilepath) throws Exception {
		FopFactory fopFactory = FopFactory.newInstance(new File(XSLFOKonstante.FOP_FILE));
		
		// Setup the XSLT transformer factory
		TransformerFactory transformerFactory = new TransformerFactoryImpl();
		
		// Point to the XSL-FO file
		File xslFile = new File(xslFilepath);

		// Create transformation source
		StreamSource transformSource = new StreamSource(xslFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new ByteArrayInputStream(xmlFile.getBytes()));

		// Initialize user agent needed for the transformation
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		
		// Create the output stream to store the results
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
		
		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

		// Resulting SAX events 
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		xslFoTransformer.transform(source, res);

		// Generate PDF file
		return new ByteArrayInputStream(outStream.toByteArray());
	}
}
