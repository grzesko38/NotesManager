package pl.arczynskiadam.core.dao;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.NoteModel;

public interface NoteRepository extends JpaRepository<NoteModel, Integer>, QueryDslPredicateExecutor<NoteModel>, JpaSpecificationExecutor<NoteModel> {
	
	@Query("FROM NoteModel n WHERE n.author = :author")
    public Page<NoteModel> findAllForUser(@Param("author") AnonymousUserModel author, Pageable pageable);
	
	@Query("FROM NoteModel n WHERE n.author.email IS NULL")
    public Page<NoteModel> findAllForAnonymous(Pageable pageable);
	
	@Modifying
	@Query("DELETE NoteModel n WHERE n.id IN (:ids)")
    public void deleteByIds(@Param("ids") Collection<Integer> ids);
	
	@Modifying
	@Query("DELETE NoteModel AS n WHERE n.author.id = :id")
    public void deleteByUserId(@Param("id") Integer id);
}
