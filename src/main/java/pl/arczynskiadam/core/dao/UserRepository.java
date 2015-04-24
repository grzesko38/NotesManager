package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer>, QueryDslPredicateExecutor<UserModel>, JpaSpecificationExecutor<UserModel> {
	
	@Query("FROM UserModel u where TYPE(u) =  AnonymousUserModel")
    public AnonymousUserModel findAllAnonymousUsers();
	
	@Query("FROM UserModel u where TYPE(u) = AnonymousUserModel AND u.nick = :nick")
    public AnonymousUserModel findAnonymousdUserByNick(@Param("nick") String userNick);
	
	@Query("FROM UserModel u where TYPE(u) = RegisteredUserModel")
    public RegisteredUserModel findAllRegisteredUsers();
	
	@Query("FROM UserModel u where TYPE(u) = RegisteredUserModel AND u.nick = :nick")
    public RegisteredUserModel findRegisteredUserByNick(@Param("nick") String userNick);
	
}
