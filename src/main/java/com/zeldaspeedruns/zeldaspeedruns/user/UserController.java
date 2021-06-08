package com.zeldaspeedruns.zeldaspeedruns.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for user related operations.
 *
 * @author Spell
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    /**
     * Instantiates a UserController
     *
     * @param service Instance of a UserService.
     */
    public UserController(@NotNull UserService service) {
        this.service = service;
    }

    /**
     * Finds and loads a user by their username, then returns a public profile of their data.
     *
     * @param username The username to search for.
     * @return Public profile of the user.
     */
    @GetMapping("/{username}")
    public UserProfile get(@PathVariable String username) {
        var user = service.loadUserByUsername(username);
        return UserProfile.from(user);
    }
}
