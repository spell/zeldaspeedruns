package com.zeldaspeedruns.zeldaspeedruns.news;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends PagingAndSortingRepository<Article, UUID> {
    Optional<Article> findArticleBySlug(String slug);
}
