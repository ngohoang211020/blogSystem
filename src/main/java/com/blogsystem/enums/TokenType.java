package com.blogsystem.enums;

import lombok.Getter;

@Getter
public enum TokenType {
    JWT("Bearer", "Json Web Token");
    private final String name;
    private final String description;

    TokenType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
