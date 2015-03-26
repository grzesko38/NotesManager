package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Integer> {
	
	@Query("FROM UserVO where NICK IS :nick")
    public UserVO findUserByNick(@Param("nick") String nick);
}
