package com.java.talent.batch12.atm.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;


public record UserPrincipal(
        String accountId,
        String username,
        String role,
        Collection<? extends GrantedAuthority> authorities,
        boolean isActive
) implements Serializable {}