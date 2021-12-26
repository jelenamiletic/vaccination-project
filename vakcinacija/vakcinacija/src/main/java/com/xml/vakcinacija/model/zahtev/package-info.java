@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_ZAHTEV,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "za", namespaceURI=XMLNamespaceKonstante.NAMESPACE_ZAHTEV),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.vakcinacija.model.zahtev;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.vakcinacija.utils.*;
