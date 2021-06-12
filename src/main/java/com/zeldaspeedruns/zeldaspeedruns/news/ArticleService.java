package com.zeldaspeedruns.zeldaspeedruns.news;

import java.util.UUID;

public interface ArticleService {
    Article loadArticleById(UUID id);

    Article loadArticleBySlug(String slug);

    Iterable<Article> loadRecentArticles();

    void saveArticle(Article article);

    void deleteArticle(UUID id);
}
