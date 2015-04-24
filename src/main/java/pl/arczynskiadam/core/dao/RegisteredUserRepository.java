package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.RegisteredUserModel;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUserModel, Integer> {
	
	@Query("FROM UserModel where NICK IS :nick")
    public RegisteredUserModel findUserByNick(@Param("nick") String nick);
}
