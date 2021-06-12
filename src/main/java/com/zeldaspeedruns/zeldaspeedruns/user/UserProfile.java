package com.zeldaspeedruns.zeldaspeedruns.user;

import java.util.UUID;

public class UserProfile {
    private final UUID id;
    private final String username;
    private final String displayName;

    public UserProfile(UUID id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    public static UserProfile from(User user) {
        return new UserProfile(user.id(), user.username(), user.displayName());
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
