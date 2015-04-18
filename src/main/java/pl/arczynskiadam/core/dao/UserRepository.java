package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.NoteVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;
import pl.arczynskiadam.core.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Integer>, QueryDslPredicateExecutor<UserVO>, JpaSpecificationExecutor<UserVO> {
	
	@Query("FROM UserVO u where TYPE(u) =  AnonymousUserVO")
    public AnonymousUserVO findAllAnonymousUsers();
	
	@Query("FROM UserVO u where TYPE(u) = AnonymousUserVO AND u.nick = :nick")
    public AnonymousUserVO findAnonymousdUserByNick(@Param("nick") String userNick);
	
	@Query("FROM UserVO u where TYPE(u) = RegisteredUserVO")
    public RegisteredUserVO findAllRegisteredUsers();
	
	@Query("FROM UserVO u where TYPE(u) = RegisteredUserVO AND u.nick = :nick")
    public RegisteredUserVO findRegisteredUserByNick(@Param("nick") String userNick);
	
}
