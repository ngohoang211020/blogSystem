package com.blogsystem.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER", "User"),
    SUPER_ADMIN("SUPER_ADMIN", "Super Admin"),
    ADMIN("ADMIN", "Admin");
    private final String name;
    private final String description;

    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
