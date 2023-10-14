package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Comments;
import entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmailOrUsername(String email, String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
