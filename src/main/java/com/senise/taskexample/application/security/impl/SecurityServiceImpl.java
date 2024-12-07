package com.senise.taskexample.application.security.impl;

import com.senise.taskexample.application.security.SecurityService;
import com.senise.taskexample.domain.exception.UserNotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * Verifica si el usuario tiene permisos para realizar una acción sobre un recurso.
     *
     * @param authentication la autenticación del usuario.
     * @param targetEmail el email del usuario objetivo (para validar si es el propio usuario).
     * @return true si el usuario tiene permisos para realizar la acción.
     */
    public boolean canAccessResource(Authentication authentication, String targetEmail) {
        return !authentication.getName().equals(targetEmail) && !isAdmin(authentication);
    }

    /**
     * Verifica si el usuario tiene rol de ADMIN.
     *
     * @param authentication la autenticación del usuario.
     * @return true si el usuario tiene rol de ADMIN.
     */
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }

    public void verifyAccess(Authentication authentication, String userEmail) {
        if (this.canAccessResource(authentication, userEmail)) {
            throw new UserNotAuthorizedException("No tienes permisos para acceder a este recurso.");
        }
    }
}
