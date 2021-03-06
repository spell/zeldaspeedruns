package com.zeldaspeedruns.zeldaspeedruns.news;

import com.zeldaspeedruns.zeldaspeedruns.model.AbstractModel;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("articles")
public record Article (@Column("id") @Id UUID id,
                      @Column("author_id") UUID authorId,
                      @Column("title") String title,
                      @Column("slug") String slug,
                      @Column("src_markdown") String source,
                      @Column("posted_on") LocalDateTime postedOn,
                      @Column("edited_on") LocalDateTime editedOn) implements AbstractModel {

    public static Article of(UUID authorId, String title, String slug, String markdownSource) {
        return new Article(null, authorId, title, slug, markdownSource, LocalDateTime.now(), null);
    }

    public Article with(@Nullable String title, @Nullable String slug, @Nullable String source) {
        return new Article(
                id,
                authorId,
                title != null ? title : this.title,
                slug != null ? slug : this.slug,
                source != null ? source : this.source,
                postedOn,
                LocalDateTime.now()
        );
    }
}
