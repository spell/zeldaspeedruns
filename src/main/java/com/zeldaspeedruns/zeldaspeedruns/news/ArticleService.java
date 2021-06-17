package com.zeldaspeedruns.zeldaspeedruns.news;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ArticleService {
    Page<Article> loadArticles(Pageable pageable);

    Article loadArticleById(UUID id);

    Article loadArticleBySlug(String slug);

    void saveArticle(Article article);

    void deleteArticle(UUID id);
}
