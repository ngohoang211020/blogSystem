package com.blogsystem.repository;

import com.blogsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u " +
            "from UserEntity u " +
            "where u.username=:username")
    Optional<UserEntity> findByUsername(String username);
}
