package com.youtube_timestamp.api.user.entity;

import com.youtube_timestamp.api.common.minterface.EnumInterface;

public enum UserRole implements EnumInterface {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER("SUPER");


    private String role;

    UserRole(String role) {
        this.role = role;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return role;
    }
}
