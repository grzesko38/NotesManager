package pl.arczynskiadam.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="USERS")
public class UserVO extends AnonymousUserVO {
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DATE_REGISTERED")
	private Date dateRegistered;
	
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

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
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
		return "UserVO [email=" + email + ", dateRegistered=" + dateRegistered
				+ ", passwordHash=" + passwordHash + ", passwordEncoding="
				+ passwordEncoding + ", passwordSalt=" + passwordSalt + ", id="
				+ id + ", nick=" + nick + "]";
	}
}
