package com.blogsystem.enums;

import lombok.Getter;

@Getter
public enum StatusType {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active");
    private final int value;
    private final String description;

    StatusType(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
