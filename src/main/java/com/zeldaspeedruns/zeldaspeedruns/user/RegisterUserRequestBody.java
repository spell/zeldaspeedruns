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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data record describing the request body for user registration requests.
 *
 * @author Spell
 */
public record RegisterUserRequestBody(@NotNull @Size(min = 4, max = 32) String username,
                                      @NotNull @Size(min = 8, max = 64) String email,
                                      @NotNull @Size(min = 4, max = 256) String password) {
}
