package pl.arczynskiadam.core.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import pl.arczynskiadam.core.model.RegisteredUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO_;
import pl.arczynskiadam.core.model.UserVO;

public class UserSpecs {
	
	public static Specification<RegisteredUserVO> byNick(final String userNick) {
		return new Specification<RegisteredUserVO>() {
            @Override
            public Predicate toPredicate(Root<RegisteredUserVO> userRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {             
                return cb.equal(userRoot.<String>get(RegisteredUserVO_.nick), userNick);
            }
		};
	}
	
	public static Specification<UserVO> registered() {
		return new Specification<UserVO>() {
            @Override
            public Predicate toPredicate(Root<UserVO> userRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Root<UserVO> person = query.from(UserVO.class);
                return cb.equal(person.type(), cb.literal(RegisteredUserVO.class));
            }
		};
	}
	
	public static Specification<UserVO> anonymous() {
		return Specifications.not(registered());
	}
}
