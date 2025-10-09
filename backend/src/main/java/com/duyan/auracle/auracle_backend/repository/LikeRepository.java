package com.duyan.auracle.auracle_backend.repository;

import com.duyan.auracle.auracle_backend.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {

    boolean existsByUserIdAndPostId(String userId, String postId);

    Optional<Like> findByUserIdAndPostId(String userId, String postId);

    void deleteByUserIdAndPostId(String userId, String postId);

    long countByPostId(String postId);

    List<Like> findByUserId(String userId);
}
