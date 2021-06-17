package com.zeldaspeedruns.zeldaspeedruns.news;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JdbcArticleService implements ArticleService {
    private final ArticleRepository repository;

    public JdbcArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Article> loadArticles(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Article loadArticleById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Article loadArticleBySlug(String slug) {
        return repository.findArticleBySlug(slug).orElseThrow();
    }

    @Override
    public void saveArticle(Article article) {
        repository.save(article);
    }

    @Override
    public void deleteArticle(UUID id) {
        repository.deleteById(id);
    }
}
