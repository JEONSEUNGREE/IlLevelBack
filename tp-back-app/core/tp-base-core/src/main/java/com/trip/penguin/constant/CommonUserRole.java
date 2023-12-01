package com.trip.penguin.constant;

public enum CommonUserRole {

    ROLE_USER("ROLE_USER"),

    ROLE_COM("ROLE_COM"),

    ROLE_ADMIN("ROLE_ADMIN"),
    ;

    private final String userRole;

    CommonUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
