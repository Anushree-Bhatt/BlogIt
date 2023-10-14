package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
