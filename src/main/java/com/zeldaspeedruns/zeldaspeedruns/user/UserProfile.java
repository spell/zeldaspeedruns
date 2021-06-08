/*
 * ZeldaSpeedRuns backend REST API Server
 * Copyright (C) 2021  Spell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
