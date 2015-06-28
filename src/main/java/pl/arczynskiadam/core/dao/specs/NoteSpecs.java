package pl.arczynskiadam.core.dao.specs;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import pl.arczynskiadam.core.model.NoteModel;
import pl.arczynskiadam.core.model.NoteModel_;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserModel_;

public class NoteSpecs {

	public static Specification<NoteModel> from(final Date from)
	{
		return new Specification<NoteModel>() {
            @Override
            public Predicate toPredicate(Root<NoteModel> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<Date> dateCreated = noteRoot.<Date> get(NoteModel_.deadline);
                return cb.greaterThanOrEqualTo(dateCreated, from);
            }
		};
	}
	
	public static Specification<NoteModel> to(final Date to)
	{
		return new Specification<NoteModel>() {
            @Override
            public Predicate toPredicate(Root<NoteModel> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Path<Date> dateCreated = noteRoot.<Date> get(NoteModel_.deadline);
                return cb.lessThanOrEqualTo(dateCreated, to);
            }
		};
	}
	
	public static Specification<NoteModel> registered()
	{
		return new Specification<NoteModel>() {
            @Override
            public Predicate toPredicate(Root<NoteModel> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Subquery<Integer> personQuery = query.subquery(Integer.class);
				final Root<RegisteredUserModel> person = personQuery.from(RegisteredUserModel.class);
				final Join<RegisteredUserModel, NoteModel> notes = person.join(UserModel_.notes);
				personQuery.select(notes.<Integer> get(NoteModel_.id));
				return cb.in(noteRoot.get(NoteModel_.id)).value(personQuery);
            }
		};
	}
	
	public static Specification<NoteModel> anonymous()
	{
		return Specifications.not(registered());
	}
	
	public static Specification<NoteModel> forNick(final String userNick)
	{
		return new Specification<NoteModel>() {
            @Override
            public Predicate toPredicate(Root<NoteModel> noteRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	final Subquery<Integer> personQuery = query.subquery(Integer.class);
				final Root<RegisteredUserModel> person = personQuery.from(RegisteredUserModel.class);
				final Join<RegisteredUserModel, NoteModel> notes = person.join(UserModel_.notes);
				personQuery.select(notes.<Integer> get(NoteModel_.id));
				personQuery.where(cb.equal(person.<String> get(UserModel_.nick), userNick));
				return cb.in(noteRoot.get(NoteModel_.id)).value(personQuery);
            }
		};
	}
}
