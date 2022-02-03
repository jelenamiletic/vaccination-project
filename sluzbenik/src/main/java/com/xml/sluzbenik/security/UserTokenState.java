package com.xml.sluzbenik.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accessToken",
        "expiresIn",
        "email",
        "role"
})
@XmlRootElement(name = "UserTokenState")
public class UserTokenState {

	@XmlElement
	protected String accessToken;
	
	@XmlElement
	protected Long expiresIn;
	
	@XmlElement
	protected String email;
	
	@XmlElement
	protected String role;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.email = null;
        this.role = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String email, String role) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
