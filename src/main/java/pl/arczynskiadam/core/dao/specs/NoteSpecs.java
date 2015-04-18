package pl.arczynskiadam.core.dao.specs;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.model.NoteVO_;
import pl.arczynskiadam.core.model.RegisteredUserVO;
import pl.arczynskiadam.core.model.UserVO_;

public class NoteSpecs {

	public static Specification<NoteVO> from(final Date from)
	{
		return new Specification<NoteVO>() {
            @Override
            public Predicate toPredicate(Root<NoteVO> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {             
                return cb.greaterThanOrEqualTo(noteRoot.<Date>get("dateCreated"), from);
            }
		};
	}
	
	public static Specification<NoteVO> registered()
	{
		return new Specification<NoteVO>() {
            @Override
            public Predicate toPredicate(Root<NoteVO> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Subquery<RegisteredUserVO> personQuery = query.subquery(RegisteredUserVO.class);
				final Root<RegisteredUserVO> person = personQuery.from(RegisteredUserVO.class);
				final Join<RegisteredUserVO, NoteVO> notes = person.join(UserVO_.notes);
				personQuery.select(notes.<RegisteredUserVO> get("author"));
				return cb.in(noteRoot.get(NoteVO_.author)).value(personQuery);
            }
		};
	}
	
	public static Specification<NoteVO> anonymous()
	{
		return Specifications.not(registered());
	}
	
	public static Specification<NoteVO> forNick(final String userNick)
	{
		return new Specification<NoteVO>() {
            @Override
            public Predicate toPredicate(Root<NoteVO> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Subquery<Integer> personQuery = query.subquery(Integer.class);
				final Root<RegisteredUserVO> person = personQuery.from(RegisteredUserVO.class);
				final Join<RegisteredUserVO, NoteVO> notes = person.join(UserVO_.notes);
				personQuery.select(notes.<Integer> get(NoteVO_.id));
				personQuery.where(cb.equal(person.<String> get(UserVO_.nick), userNick));
				return cb.in(noteRoot.get(NoteVO_.id)).value(personQuery);
            }
		};
	}
}
