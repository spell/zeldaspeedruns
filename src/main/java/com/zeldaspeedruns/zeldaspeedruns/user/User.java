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
        // We must retrieve the display name attribute from the user, if it exists.
        var displayName = userRepresentation.getUsername();
        var attributes = userRepresentation.getAttributes();
        if (attributes != null && attributes.containsKey(DISPLAY_NAME_ATTRIBUTE)) {
            var displayNameAttribute =  attributes.get(DISPLAY_NAME_ATTRIBUTE);
            if (displayNameAttribute != null && !displayNameAttribute.isEmpty()) {
                displayName = displayNameAttribute.get(0);
            }
        }

        return new User(
                UUID.fromString(userRepresentation.getId()),
                userRepresentation.getUsername(),
                userRepresentation.getEmail(),
                displayName
        );
    }
}
