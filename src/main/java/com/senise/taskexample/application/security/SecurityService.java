package com.senise.taskexample.application.security;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    boolean canAccessResource(Authentication authentication, String targetEmail);
    boolean isAdmin(Authentication authentication);
    void verifyAccess(Authentication authentication, String userEmail);
}