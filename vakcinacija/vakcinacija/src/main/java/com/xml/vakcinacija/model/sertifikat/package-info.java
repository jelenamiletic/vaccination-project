@XmlSchema(
        namespace = XMLNamespaceKonstante.NAMESPACE_SERTIFIKAT,
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "za", namespaceURI=XMLNamespaceKonstante.NAMESPACE_SERTIFIKAT),
                @javax.xml.bind.annotation.XmlNs(prefix="pred", namespaceURI=XMLNamespaceKonstante.PREDICATE),
                @javax.xml.bind.annotation.XmlNs(prefix = "ct", namespaceURI=XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES),
                @javax.xml.bind.annotation.XmlNs(prefix="xs", namespaceURI="http://www.w3.org/2001/XMLSchema")}
)
package com.xml.vakcinacija.model.sertifikat;

import javax.xml.bind.annotation.XmlSchema;
import com.xml.vakcinacija.utils.*;
