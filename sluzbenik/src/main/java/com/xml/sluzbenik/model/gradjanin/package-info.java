@XmlSchema( 
		   namespace = XMLNamespaceKonstante.NAMESPACE_GRADJANIN,
		   elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		   xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "gr", namespaceURI=XMLNamespaceKonstante.NAMESPACE_GRADJANIN),
				   	@javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
        			@javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
          )
package com.xml.sluzbenik.model.gradjanin;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.sluzbenik.utils.*;
