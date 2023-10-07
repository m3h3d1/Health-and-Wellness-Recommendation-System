package com.healthapp.recommendationservicemanual.utilities.token;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class IDExtractor {
    /**
     * Retrieves the user's UUID from the currently authenticated principal.
     *
     * @return The UUID of the authenticated user.
     */
    public static UUID getUserID() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}