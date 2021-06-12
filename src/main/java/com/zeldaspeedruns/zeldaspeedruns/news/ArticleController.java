package com.zeldaspeedruns.zeldaspeedruns.news;

import com.zeldaspeedruns.zeldaspeedruns.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news/articles")
public class ArticleController {
    private final ArticleService service;
    private final UserService userService;

    public ArticleController(ArticleService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseBody>> list() {
        ArrayList<ArticleResponseBody> articles = new ArrayList<>();
        for (var article : service.loadRecentArticles()) {
            var user = userService.loadUserById(article.authorId());
            articles.add(ArticleResponseBody.from(article, user));
        }
        return ResponseEntity.ok(articles);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ArticleRequestBody body,
                                       @AuthenticationPrincipal Principal principal) {
        service.saveArticle(Article.of(UUID.fromString(principal.getName()), body.title(), body.slug(), body.source()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseBody> detail(@PathVariable String slug) {
        var article = service.loadArticleBySlug(slug);
        var user = userService.loadUserById(article.authorId());
        return ResponseEntity.ok(ArticleResponseBody.from(article, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ArticleRequestBody body,
                                       @AuthenticationPrincipal Principal principal) {
        Article article = service.loadArticleById(id);
        service.saveArticle(article.edit(body));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteArticle(id);
    }
}
