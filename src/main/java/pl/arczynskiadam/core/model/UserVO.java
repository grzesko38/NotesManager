package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE")
@Table(name="USERS")
public abstract class UserVO implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name="NICK", unique=true)
	protected String nick;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	protected Set<NoteVO> notes = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Fetch(FetchMode.SELECT)
	private Set<UserRoleVO> userRoles = new HashSet<UserRoleVO>(0);
	
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

	public Set<NoteVO> getNotes() {
		return notes;
	}
	
	public void addNote(NoteVO note) {
		if (notes.contains(note)) {
			return;
		}
		notes.add(note);
		note.setAuthor(this);
	}
	
	public Set<UserRoleVO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleVO> userRoles) {
		this.userRoles = userRoles;
	}
}
