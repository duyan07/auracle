package com.duyan.auracle.auracle_backend.repository;

import com.duyan.auracle.auracle_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByClerkUserId(String clerkUserId);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByClerkUserId(String clerkUserId);

    List<User> findByDisplayNameContaining(String keyword);
}
