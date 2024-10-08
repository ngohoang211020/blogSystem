package com.blogsystem.repository;

import com.blogsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    @Query("SELECT r.code from RoleEntity r " +
            "where exists(select 1 from UserRoleEntity ur where ur.user.userId = :userId and r.roleId= ur.role.roleId)")
    List<String> findAllRoleCodeByUserId(UUID userId);
}
