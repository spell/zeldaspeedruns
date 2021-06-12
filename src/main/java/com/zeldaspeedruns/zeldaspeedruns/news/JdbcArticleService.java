package com.zeldaspeedruns.zeldaspeedruns.news;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.UUID;

@Service
public class JdbcArticleService implements ArticleService {
    private final ArticleRepository repository;

    public JdbcArticleService(ArticleRepository repository) {
        this.repository = repository;
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
    public Iterable<Article> loadRecentArticles() {
        return repository.findAll(PageRequest.of(0, 20, Sort.by("postedOn").descending())).toList();
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
