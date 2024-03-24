package com.blogsystem.entity;

import com.blogsystem.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;
@Entity
@Table(name = "role")
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID roleId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType code;

    @Column(nullable = false)
    private String name;

    private int status;

    private String description;
    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @PrePersist
    public void prePersist(){
        this.createdAt= OffsetDateTime.now();

    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt=OffsetDateTime.now();
    }
}
