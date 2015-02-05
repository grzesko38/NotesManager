package pl.arczynskiadam.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.arczynskiadam.core.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Integer> {
}
