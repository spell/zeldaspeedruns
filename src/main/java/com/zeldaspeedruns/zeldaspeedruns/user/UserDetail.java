package com.zeldaspeedruns.zeldaspeedruns.user;

import java.util.UUID;

public class UserDetail {
    private final UUID id;
    private final String username;
    private final String displayName;

    public UserDetail(UUID id, String username, String displayName) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    public static UserDetail from(User user) {
        return new UserDetail(user.id(), user.username(), user.displayName());
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
