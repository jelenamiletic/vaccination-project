package com.xml.vakcinacija.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.security.core.GrantedAuthority;

import com.xml.vakcinacija.utils.XMLNamespaceKonstante;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "RoleName"
})
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required = true, namespace = XMLNamespaceKonstante.NAMESPACE_COMMON_TYPES)
	protected String RoleName;
	
	@Override
	public String getAuthority() {
		return RoleName;
	}
	
	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
}

