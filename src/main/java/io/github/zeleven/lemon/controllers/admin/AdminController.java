package io.github.zeleven.lemon.controllers.admin;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.entities.Tag;
import io.github.zeleven.lemon.services.ArticleService;
import io.github.zeleven.lemon.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return "redirect:/admin/article";
    }

    @RequestMapping(value = "article", method = RequestMethod.GET)
    public String articleAdmin(Model model) {
        List<Article> articles = articleService.findAllByCreateTime();
        List<Tag> tags = tagService.findAllByCreateTime();
        model.addAttribute("articles", articles);
        model.addAttribute("tags", tags);
        return "admin/article_admin";
    }

    @RequestMapping(value = "tag", method = RequestMethod.GET)
    public String tagAdmin(Model model) {
        List<Tag> tags = tagService.findAllByCreateTime();
        model.addAttribute("tags", tags);
        return "admin/tag_admin";
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    public String account(Model model) {
        return "admin/account";
    }
}
