package pl.arczynskiadam.core.model;

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
@Table(name = "USER_ROLES", uniqueConstraints = @UniqueConstraint(columnNames = { "ROLE", "USER_NICK" }))
public class UserRoleVO {
	private Integer id;
	private AnonymousUserVO user;
	private String role;
 
	public UserRoleVO() {
	}
 
	public UserRoleVO(UserVO user, String role) {
		this.user = user;
		this.role = role;
	}
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({    
		@JoinColumn(name = "USER_NICK", referencedColumnName = "NICK", nullable = false),
	})
	public AnonymousUserVO getUser() {
		return this.user;
	}
 
	public void setUser(AnonymousUserVO user) {
		this.user = user;
	}
 
	@Column(name = "ROLE", nullable = false)
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
}
