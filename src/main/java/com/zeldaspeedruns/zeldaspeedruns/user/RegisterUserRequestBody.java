package com.zeldaspeedruns.zeldaspeedruns.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegisterUserRequestBody(@NotNull @Size(min = 4, max = 32) String username,
                                      @NotNull @Size(min = 8, max = 64) String email,
                                      @NotNull @Size(min = 4, max = 256) String password) {
}
