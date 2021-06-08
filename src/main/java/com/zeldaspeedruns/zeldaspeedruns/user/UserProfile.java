package com.zeldaspeedruns.zeldaspeedruns.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Public facing profile of a user.
 *
 * @author Spell
 */
public record UserProfile(@JsonProperty UUID id,
                          @JsonProperty String username,
                          @JsonProperty String displayName) {
    /**
     * Constructs a user profile from a user model.
     *
     * @param user Instance of the User model to use.
     * @return User profile constructed from the model.
     */
    static UserProfile from(@NotNull User user) {
        return new UserProfile(user.id(), user.username(), user.displayName());
    }
}
