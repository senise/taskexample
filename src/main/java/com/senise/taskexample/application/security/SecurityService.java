package com.senise.taskexample.application.security;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    public boolean canAccessResource(Authentication authentication, String targetEmail);
    boolean isAdmin(Authentication authentication);
}