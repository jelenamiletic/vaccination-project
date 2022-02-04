package com.xml.vakcinacija.model.sluzbenik;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.xml.vakcinacija.model.PunoIme;
import com.xml.vakcinacija.model.Role;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "PunoIme",
        "Email",
        "Lozinka",
        "Enabled",
        "Roles",
        "lastPasswordResetDate"
})
@XmlRootElement(name = "Sluzbenik")
public class Sluzbenik implements UserDetails {
	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
    protected PunoIme PunoIme;
	
	@XmlElement(required = true)
	protected String Email;
	
	@XmlElement(required = true)
	protected String Lozinka;
	
	@XmlElement
	protected boolean Enabled;
	
	@XmlElement
	protected List<Role> Roles;
	
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar lastPasswordResetDate;
    
	public PunoIme getPunoIme() {
		return PunoIme;
	}

	public void setPunoIme(PunoIme punoIme) {
		PunoIme = punoIme;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getLozinka() {
		return Lozinka;
	}

	public void setLozinka(String lozinka) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.setLastPasswordResetDate(date2);
		Lozinka = lozinka;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	public void setLastPasswordResetDate(XMLGregorianCalendar lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Roles;
	}

	@Override
	public String getPassword() {
		return Lozinka;
	}

	@Override
	public String getUsername() {
		return Email;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public boolean isEnabled() {
		return Enabled;
	}
	
	public XMLGregorianCalendar getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	
	public void setRoles(List<Role> roles) {
        this.Roles = roles;
    }
    
    public List<Role> getRoles() {
       return Roles;
    }
}
