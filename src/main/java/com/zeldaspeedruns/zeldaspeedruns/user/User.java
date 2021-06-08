package com.zeldaspeedruns.zeldaspeedruns.user;

import org.jetbrains.annotations.NotNull;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.UUID;

/**
 * Data model of a user.
 *
 * @author Spell
 */
public record User(UUID id,
                   String username,
                   String email,
                   String displayName) {
    public static final String DISPLAY_NAME_ATTRIBUTE = "display-name";

    /**
     * Returns user constructed from a Keycloak user representation.
     *
     * @param userRepresentation User representation of the user.
     * @return User instance.
     */
    static User from(@NotNull UserRepresentation userRepresentation) {
        var displayName = userRepresentation.getUsername();
        var attributes = userRepresentation.getAttributes().get(DISPLAY_NAME_ATTRIBUTE);
        if (attributes != null && !attributes.isEmpty()) {
            displayName = attributes.get(0);
        }

        return new User(
                UUID.fromString(userRepresentation.getId()),
                userRepresentation.getUsername(),
                userRepresentation.getEmail(),
                displayName
        );
    }
}
