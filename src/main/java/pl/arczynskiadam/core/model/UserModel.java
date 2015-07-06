package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE")
@Table(name="USERS")
public abstract class UserModel implements Serializable {

	private static final long serialVersionUID = 6441378387355291115L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name="NICK")
	protected String nick;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	protected Set<NoteModel> notes = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
	private Set<UserRoleModel> userRoles = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
	private UserPreferencesModel userPreferences;
	
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

	public Set<NoteModel> getNotes() {
		return notes;
	}
	
	public void addNote(NoteModel note) {
		if (notes.contains(note)) {
			return;
		}
		notes.add(note);
		note.setAuthor(this);
	}
	
	public void addUserRole(UserRoleModel role) {
		if (userRoles.contains(role)) {
			return;
		}
		userRoles.add(role);
		role.setUser(this);
	}
	
	public Set<UserRoleModel> getUserRoles() {
		return userRoles;
	}

	public UserPreferencesModel getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(UserPreferencesModel userPreferences) {
		if (userPreferences.equals(this.userPreferences)) {
			return;
		}
		this.userPreferences = userPreferences;
		userPreferences.setUser(this);
	}

	public abstract boolean isAnonymous();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}
}
