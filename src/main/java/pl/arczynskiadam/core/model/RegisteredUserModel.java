package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import pl.arczynskiadam.core.model.converters.LocalDateTimeConverter;


@Entity
@Table(name="USERS")
@DiscriminatorValue("RegisteredUser")
public class RegisteredUserModel extends AnonymousUserModel implements Serializable {
	
	private static final long serialVersionUID = -9104860696486228861L;

	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DATE_REGISTERED")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dateRegistered;
	
	@Column(name="PASSWORD_HASH")
	private String passwordHash;
	
	@Column(name="PASSWORD_ENCODING")
	private String passwordEncoding;
	
	@Column(name="PASSWORD_SALT")
	private String passwordSalt;
	
	@Column(name="ENABLED")
	private boolean enabled;

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(LocalDateTime dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPasswordEncoding() {
		return passwordEncoding;
	}

	public void setPasswordEncoding(String passwordEncoding) {
		this.passwordEncoding = passwordEncoding;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "RegisteredUserModel [email=" + email + ", dateRegistered=" + dateRegistered
				+ ", passwordHash=" + passwordHash + ", passwordEncoding="
				+ passwordEncoding + ", passwordSalt=" + passwordSalt + ", id="
				+ id + ", nick=" + nick + "]";
	}

	@Override
	public boolean isAnonymous() {
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((dateRegistered == null) ? 0 : dateRegistered.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegisteredUserModel other = (RegisteredUserModel) obj;
		if (dateRegistered == null) {
			if (other.dateRegistered != null)
				return false;
		} else if (!dateRegistered.equals(other.dateRegistered))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}
