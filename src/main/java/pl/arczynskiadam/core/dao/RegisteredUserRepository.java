package pl.arczynskiadam.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserVO;
import pl.arczynskiadam.core.model.RegisteredUserVO;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUserVO, Integer> {
	
	@Query("FROM UserVO where NICK IS :nick")
    public RegisteredUserVO findUserByNick(@Param("nick") String nick);
	
	public List<RegisteredUserVO> findByEmailNotNull();
	public List<RegisteredUserVO> findByEmailIsNull();
}
