package com.zeldaspeedruns.zeldaspeedruns.user;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public User loadUserById(UUID id) throws UserNotFoundException {
        try {
            var userResource = realm.users().get(id.toString());
            return User.from(userResource.toRepresentation());
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UserNotFoundException {
        // Perform an exact match only search for the username.
        var results = realm.users().search(username, true);
        if (results.isEmpty()) {
            throw new UserNotFoundException(String.format("no user with username '%s' exists", username));
        }
        return User.from(results.get(0));
    }
}
