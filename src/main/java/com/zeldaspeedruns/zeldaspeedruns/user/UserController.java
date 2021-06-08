package com.zeldaspeedruns.zeldaspeedruns.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Registers a new user account.
     *
     * @param body The request body.
     * @return The public user profile of the newly created user.
     */
    @PostMapping("/register")
    public ResponseEntity<UserProfile> register(@RequestBody @Valid RegisterUserRequestBody body) {
        var user = service.createUser(body.username(), body.email(), body.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserProfile.from(user));
    }

    /**
     * Prints validation errors to a map.
     *
     * @param exception Validation exception containing the binding results.
     * @return Map of errors for each property of the request body.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
