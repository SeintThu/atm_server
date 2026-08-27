package com.java.talent.batch12.atm.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService {

    /**
     * Extracts the populated session object directly out of the Security Thread.
     * Use this inside your business services for authenticated endpoints.
     */
    public UserPrincipal getCurrentUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        }

        throw new RuntimeException("Unauthenticated user context access missing.");
    }

    public String getAccountIdByCurrentUser() {
        return getCurrentUserPrincipal().accountId();
    }

    public String getUserNameByLoginToken() {

        return getCurrentUserPrincipal().username();
    }


    public String getAccountRoleFromLoginToken() {
        return getCurrentUserPrincipal().role();
    }
}