package com.berkozmen.loan_application_system.model;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_CLIENT;

    @Override
    public String getAuthority() {
        return name().toString();
    }
}
