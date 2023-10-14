package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {

}
