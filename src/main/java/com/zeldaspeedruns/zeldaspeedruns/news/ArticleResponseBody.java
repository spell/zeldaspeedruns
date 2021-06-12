package com.zeldaspeedruns.zeldaspeedruns.news;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zeldaspeedruns.zeldaspeedruns.user.User;
import com.zeldaspeedruns.zeldaspeedruns.user.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseBody {
    private final UUID id;
    private final UserProfile author;
    private final String title;
    private final String slug;
    private final String source;
    private final LocalDateTime postedOn;
    private final LocalDateTime editedOn;

    public ArticleResponseBody(UUID id, UserProfile author, String title, String slug, String source, LocalDateTime postedOn, LocalDateTime editedOn) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.slug = slug;
        this.source = source;
        this.postedOn = postedOn;
        this.editedOn = editedOn;
    }

    static ArticleResponseBody from(@NotNull Article article, @NotNull User user) {
        return new ArticleResponseBody(
                article.id(),
                UserProfile.from(user),
                article.title(),
                article.slug(),
                article.source(),
                article.postedOn(),
                article.editedOn()
        );
    }

    public UUID getId() {
        return id;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public LocalDateTime getEditedOn() {
        return editedOn;
    }
}
