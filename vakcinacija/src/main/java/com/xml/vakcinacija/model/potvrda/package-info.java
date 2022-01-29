@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_POTVRDA,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "po", namespaceURI=XMLNamespaceKonstante.NAMESPACE_POTVRDA),
				   @javax.xml.bind.annotation.XmlNs(prefix="pred", namespaceURI=XMLNamespaceKonstante.PREDICATE),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.vakcinacija.model.potvrda;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.vakcinacija.utils.*;
