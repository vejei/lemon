package io.github.zeleven.lemon.controllers;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/article/{articleId}")
    public String articleDetail(Model model, @PathVariable Integer articleId) {
        Article article = articleService.findOne(articleId);
        model.addAttribute("article", article);
        return "detail";
    }
}
