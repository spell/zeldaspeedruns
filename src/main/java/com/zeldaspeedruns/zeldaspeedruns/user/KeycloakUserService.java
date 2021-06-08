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

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
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
            var userResource = realm.users().get(id.toString());
            return User.from(userResource.toRepresentation());
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Cacheable("users")
    public User loadUserByUsername(String username) throws UserNotFoundException {
        // Perform an exact match only search for the username.
        var results = realm.users().search(username, true);
        if (results.isEmpty()) {
            throw new UserNotFoundException(String.format("no user with username '%s' exists", username));
        }
        return User.from(results.get(0));
    }

    @Override
    public User createUser(String username, String email, String password) throws UserExistsException {
        // Keycloak users can have multiple credentials so we must define a password credential.
        var credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        // Define the new user.
        var user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        var response = realm.users().create(user);
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            throw new RuntimeException("Could not create user, HTTP status: " + response.getStatus());
        }

        // We must now retrieve our new user to send them a verification email.
        var userResource = realm.users().get(CreatedResponseUtil.getCreatedId(response));
        userResource.sendVerifyEmail();
        return User.from(userResource.toRepresentation());
    }
}
