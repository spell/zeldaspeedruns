package com.zeldaspeedruns.zeldaspeedruns;

import com.zeldaspeedruns.zeldaspeedruns.news.ArticleDetailConverter;
import com.zeldaspeedruns.zeldaspeedruns.news.ArticleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZeldaSpeedRunsController {
    private final ArticleService articleService;
    private final ArticleDetailConverter articleConverter;

    public ZeldaSpeedRunsController(ArticleService articleService, ArticleDetailConverter articleConverter) {
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @GetMapping("/")
    public String index(Model model, ArticleDetailConverter converter) {
        var pageRequest = PageRequest.of(0, 5, Sort.by("postedOn"));
        var articles = articleService.loadArticles(pageRequest);
        model.addAttribute("articles", articleConverter.fromModels(articles.toList()));
        return "index";
    }
}
