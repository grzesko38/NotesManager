package pl.arczynskiadam.core.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel_;
import pl.arczynskiadam.core.model.UserModel;

public class UserSpecs {
	
	public static Specification<RegisteredUserModel> byNick(final String userNick) {
		return new Specification<RegisteredUserModel>() {
            @Override
            public Predicate toPredicate(Root<RegisteredUserModel> userRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {             
                return cb.equal(userRoot.<String>get(RegisteredUserModel_.nick), userNick);
            }
		};
	}
	
	public static Specification<UserModel> registered() {
		return new Specification<UserModel>() {
            @Override
            public Predicate toPredicate(Root<UserModel> userRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Root<UserModel> person = query.from(UserModel.class);
                return cb.equal(person.type(), cb.literal(RegisteredUserModel.class));
            }
		};
	}
	
	public static Specification<UserModel> anonymous() {
		return Specifications.not(registered());
	}
}
