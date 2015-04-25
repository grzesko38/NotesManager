package pl.arczynskiadam.core.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
@DiscriminatorValue("AnonymousUser")
public class AnonymousUserModel extends UserModel implements Serializable {
	
	@Override
	public String toString() {
		return "AnonymousUserModel [id=" + id + ", nick=" + nick + "]";
	}
}
