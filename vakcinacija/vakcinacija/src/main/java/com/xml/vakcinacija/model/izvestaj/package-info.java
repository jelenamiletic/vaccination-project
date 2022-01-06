@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_IZVESTAJ,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "iz", namespaceURI=XMLNamespaceKonstante.NAMESPACE_IZVESTAJ),
				   	@javax.xml.bind.annotation.XmlNs(prefix="pred", namespaceURI=XMLNamespaceKonstante.PREDICATE),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.vakcinacija.model.izvestaj;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.vakcinacija.utils.*;
