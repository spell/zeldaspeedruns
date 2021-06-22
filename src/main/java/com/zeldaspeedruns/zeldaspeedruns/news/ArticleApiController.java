package com.zeldaspeedruns.zeldaspeedruns.news;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news/articles")
public class ArticleApiController {
    private final ArticleService service;
    private final ArticleDetailConverter articleConverter;

    public ArticleApiController(ArticleService service, ArticleDetailConverter articleConverter) {
        this.service = service;
        this.articleConverter = articleConverter;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDetail>> list(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by("postedOn").descending())
        );

        var articles = service.loadArticles(pageable);
        return ResponseEntity.ok(articleConverter.fromModels(articles.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateArticleRequest body,
                                       @AuthenticationPrincipal Principal principal) {
        service.saveArticle(Article.of(UUID.fromString(principal.getName()), body.title(), body.slug(), body.source()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleDetail> detail(@PathVariable String slug) {
        var article = service.loadArticleBySlug(slug);
        return ResponseEntity.ok(articleConverter.from(article));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CreateArticleRequest body,
                                       @AuthenticationPrincipal Principal principal) {
        Article article = service.loadArticleById(id);
        if (!article.authorId().equals(UUID.fromString(principal.getName()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.saveArticle(article.with(body.title(), body.slug(), body.source()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
