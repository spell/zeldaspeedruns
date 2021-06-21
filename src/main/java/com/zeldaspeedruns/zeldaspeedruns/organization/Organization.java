package com.zeldaspeedruns.zeldaspeedruns.organization;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("organizations")
public record Organization(@Column("id") @Id UUID id,
                           @Column("name") String name,
                           @Column("slug") String slug) {
    public static Organization of(String name, String slug) {
        return new Organization(null, name, slug);
    }
}
