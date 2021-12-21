@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "in", namespaceURI=XMLNamespaceKonstante.NAMESPACE_INTERESOVANJE),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.vakcinacija.model.interesovanje;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.vakcinacija.utils.*;
