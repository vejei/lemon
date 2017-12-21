package io.github.zeleven.lemon.controllers.admin;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writePage(Model model) {
        model.addAttribute("article", new Article());
        return "admin/article/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String create() {
        return "redirect:/admin";
    }
}
