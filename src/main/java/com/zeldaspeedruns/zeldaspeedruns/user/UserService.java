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

import java.util.UUID;

/**
 * UserService details a way of interacting with an underlying user data storage.
 *
 * @author Spell
 */
public interface UserService {
    /**
     * Loads user from the backing data storage by their identifier.
     *
     * @param id The user's unique identifier.
     * @return User instance if the user is found.
     * @throws UserNotFoundException Thrown if no user with that identifier exists.
     */
    User loadUserById(UUID id) throws UserNotFoundException;
}
