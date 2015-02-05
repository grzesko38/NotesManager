package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.arczynskiadam.core.model.AnonymousUserVO;

public interface AnonymousUserRepository extends JpaRepository<AnonymousUserVO, Integer> {
	
	@Query("FROM AnonymousUserVO where NICK IS :nick")
    public AnonymousUserVO findAnonymousUserByNick(@Param("nick") String nick);
}
