package pl.arczynskiadam.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_ROLES", uniqueConstraints = @UniqueConstraint(columnNames = { "ROLE", "USER_FK" }))
public class UserRoleModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")	
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_FK")
	private UserModel user;
	
	@Column(name = "ROLE", nullable = false)
	private String role;
 
	public UserRoleModel() {}
 
	public Integer getId() {
		return this.id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
 
//	@JoinColumns({    
//		@JoinColumn(name = "USER_FK"/*, referencedColumnName = "ID"*/, nullable = false),
//	})
	public UserModel getUser() {
		return this.user;
	}
 
	public void setUser(UserModel user) {
		this.user = user;
		user.addUserRole(this);
	}
 
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
}
