package com.nguyean.auracle.repository;

import com.nguyean.auracle.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDateTimeDesc();
    List<Post> findByUserIdOrderByDateTimeDesc(Long userId);
}
