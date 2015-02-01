package pl.arczynskiadam.core.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class UserVO {

	@Id
	@Column(name="ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name="NICK")
	private String nick;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<NoteVO> notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

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
	
	public Set<NoteVO> getNotes() {
		return notes;
	}

	public void setNotes(Set<NoteVO> notes) {
		this.notes = notes;
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

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", nick=" + nick + ", email=" + email
				+ ", dateRegistered=" + dateRegistered + ", passwordHash="
				+ passwordHash + ", passwordEncoding=" + passwordEncoding
				+ ", passwordSalt=" + passwordSalt + "]";
	}
}
