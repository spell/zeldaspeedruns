package com.zeldaspeedruns.zeldaspeedruns.user;

import org.springframework.cache.annotation.Cacheable;

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

    /**
     * Loads user from the backing data storage by their username.
     *
     * @param username The user's username.
     * @return User instance if the user is found.
     * @throws UserNotFoundException Thrown if no user with that username exists.
     */
    User loadUserByUsername(String username) throws UserNotFoundException;

    /**
     * Creates a new user and saves it in the backing data storage.
     *
     * @param username The desired username of the user to create.
     * @param email A valid email address.
     * @param password The desired password of the user to create.
     * @return The newly created user.
     * @throws UserExistsException Thrown when a user with the username and/or password already exists.
     */
    User createUser(String username, String email, String password) throws UserExistsException;
}
