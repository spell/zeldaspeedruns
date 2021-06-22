package com.zeldaspeedruns.zeldaspeedruns.news;

import com.zeldaspeedruns.zeldaspeedruns.model.ModelConverter;
import com.zeldaspeedruns.zeldaspeedruns.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class ArticleDetailConverter implements ModelConverter<Article, ArticleDetail> {
    private final UserService userService;

    public ArticleDetailConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ArticleDetail from(Article article) {
        return ArticleDetail.from(article, userService.loadUserById(article.authorId()));
    }

    @Override
    public Article from(ArticleDetail model) {
        return new Article(
                model.getId(),
                model.getAuthor().getId(),
                model.getTitle(),
                model.getSlug(),
                model.getSource(),
                model.getPostedOn(),
                model.getEditedOn()
        );
    }
}
