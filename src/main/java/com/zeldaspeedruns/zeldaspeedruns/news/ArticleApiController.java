package com.zeldaspeedruns.zeldaspeedruns.news;

import com.zeldaspeedruns.zeldaspeedruns.user.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news/articles")
public class ArticleApiController {
    private final ArticleService service;
    private final UserService userService;

    public ArticleApiController(ArticleService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDetail>> list(Pageable pageable) {
        ArrayList<ArticleDetail> articles = new ArrayList<>();

        // Pageables are immutable, thus:
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by("postedOn").descending())
        );

        for (var article : service.loadArticles(pageable)) {
            var user = userService.loadUserById(article.authorId());
            articles.add(ArticleDetail.from(article, user));
        }
        return ResponseEntity.ok(articles);
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
        var user = userService.loadUserById(article.authorId());
        return ResponseEntity.ok(ArticleDetail.from(article, user));
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
