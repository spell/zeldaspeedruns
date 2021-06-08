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

import org.keycloak.representations.idm.UserRepresentation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Interface to a user model.
 *
 * @author Spell
 */
public record User(UUID id, String username, String email, String displayName) implements Serializable {
    public static final String DISPLAY_NAME_ATTRIBUTE = "display-name";

    public static User fromRepresentation(UserRepresentation representation) {
        var displayName = representation.firstAttribute(DISPLAY_NAME_ATTRIBUTE);
        if (displayName == null) {
            displayName = representation.getUsername();
        }

        return new User(
                UUID.fromString(representation.getId()),
                representation.getUsername(),
                representation.getEmail(),
                displayName
        );
    }

    public User withDisplayName(String displayName) {
        return new User(id, username, email, displayName);
    }
}
