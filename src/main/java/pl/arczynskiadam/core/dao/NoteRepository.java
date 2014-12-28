package pl.arczynskiadam.core.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.NoteVO;

public interface NoteRepository extends JpaRepository<NoteVO, Integer>, QueryDslPredicateExecutor<NoteVO> {
	
	@Modifying
	@Query("DELETE NoteVO N where N.id IN (:ids)")
    public void deleteByIds(@Param("ids") Set<Integer> ids);
}
