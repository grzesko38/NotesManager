package pl.arczynskiadam.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="USER_TYPE")
@DiscriminatorValue("AnonymousUserVO")
public class AnonymousUserModel extends UserModel implements Serializable {
	
	@Override
	public String toString() {
		return "AnonymousUserVO [id=" + id + ", nick=" + nick + "]";
	}
}
