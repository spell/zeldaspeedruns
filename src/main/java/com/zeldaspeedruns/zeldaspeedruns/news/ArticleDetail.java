package com.zeldaspeedruns.zeldaspeedruns.news;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zeldaspeedruns.zeldaspeedruns.user.User;
import com.zeldaspeedruns.zeldaspeedruns.user.UserDetail;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDetail {
    private final UUID id;
    private final UserDetail author;
    private final String title;
    private final String slug;
    private final String source;
    private final LocalDateTime postedOn;
    private final LocalDateTime editedOn;

    public ArticleDetail(UUID id, UserDetail author, String title, String slug, String source, LocalDateTime postedOn, LocalDateTime editedOn) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.slug = slug;
        this.source = source;
        this.postedOn = postedOn;
        this.editedOn = editedOn;
    }

    static ArticleDetail from(@NotNull Article article, @NotNull User user) {
        return new ArticleDetail(
                article.id(),
                UserDetail.from(user),
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

    public UserDetail getAuthor() {
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
