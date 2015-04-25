package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserModel;
import pl.arczynskiadam.core.model.RegisteredUserModel;
import pl.arczynskiadam.core.model.UserModel;
import pl.arczynskiadam.core.model.UserRoleModel;

public interface RoleRepository extends JpaRepository<UserRoleModel, Integer>, JpaSpecificationExecutor<UserRoleModel> {
	

	
}
