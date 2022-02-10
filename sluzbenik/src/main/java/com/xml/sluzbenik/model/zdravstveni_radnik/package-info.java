@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_ZDRAVSTVENI_RADNIK,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "zd", namespaceURI=XMLNamespaceKonstante.NAMESPACE_ZDRAVSTVENI_RADNIK),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.sluzbenik.model.zdravstveni_radnik;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.sluzbenik.utils.*;
