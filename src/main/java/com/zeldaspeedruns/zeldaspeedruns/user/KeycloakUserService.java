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

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.common.util.Base64Url;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * KeycloakUserService is an implementation of UserService that operates on a Keycloak user storage through the
 * Keycloak administration API.
 *
 * @author Spell
 */
@Service
public class KeycloakUserService implements UserService {
    private final RealmResource realm;

    /**
     * Instantiates a KeycloakUserService.
     *
     * @param keycloak Keycloak administration client instance.
     * @param realm    The name of the Keycloak realm to operate on.
     */
    public KeycloakUserService(final Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.realm = keycloak.realm(realm);
    }

    @Override
    @Cacheable("users")
    public User loadUserById(UUID id) throws UserNotFoundException {
        try {
            var userResource = getUserResource(id);
            return User.fromRepresentation(userResource.toRepresentation());
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage(), e.getCause());
        }
    }

    public UserResource getUserResource(UUID id) {
        return realm.users().get(id.toString());
    }
}
