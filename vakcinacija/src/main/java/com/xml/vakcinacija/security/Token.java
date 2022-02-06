package com.xml.vakcinacija.security;

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
        "role",
        "jmbg"
})
@XmlRootElement(name = "Token")
public class Token {

	@XmlElement
	protected String accessToken;
	
	@XmlElement
	protected Long expiresIn;
	
	@XmlElement
	protected String email;
	
	@XmlElement
	protected String role;
	
	@XmlElement
	protected String jmbg;

    public Token() {
        this.accessToken = null;
        this.expiresIn = null;
        this.email = null;
        this.role = null;
        this.jmbg = null;
    }

    public Token(String accessToken, long expiresIn, String email, String role, String jmbg) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.email = email;
        this.role = role;
        this.jmbg = jmbg;
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
}
