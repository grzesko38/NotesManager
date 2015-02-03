package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Integer>, QueryDslPredicateExecutor<NoteVO> {
}
