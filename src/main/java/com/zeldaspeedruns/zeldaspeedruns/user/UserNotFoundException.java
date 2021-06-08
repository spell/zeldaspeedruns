package com.zeldaspeedruns.zeldaspeedruns.user;

/**
 * Exception detailing the absence of a user in the backing storage.
 *
 * @author Jesse
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
