package com.blogsystem.repository;

import com.blogsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    @Query("SELECT r.code from RoleEntity r " +
            "where exists(select 1 from UserRoleEntity ur where ur.user.userId = :userId)")
    List<String> findAllRoleCodeByUserId(UUID userId);
}
