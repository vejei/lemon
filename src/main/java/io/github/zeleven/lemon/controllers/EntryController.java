package io.github.zeleven.lemon.controllers;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.entities.Tag;
import io.github.zeleven.lemon.services.ArticleService;
import io.github.zeleven.lemon.services.TagService;
import io.github.zeleven.lemon.utils.GitHub;
import io.github.zeleven.lemon.utils.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EntryController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

    @GetMapping(value = "/")
    public String home(Model model, @PageableDefault(size = 15, sort = {"createTime"},
                               direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Article> page = articleService.findAll(pageable);
        List<Tag> tags = tagService.findAllByCreateTime();
        if (tags.size() > 10) {
            tags = tags.subList(0, tags.size() - 1);
        }
        page.getTotalPages();
        pageable.getPageNumber();
        model.addAttribute("pageable", pageable);
        int pageCount = page.getTotalPages() >= 10 ? 10 : page.getTotalPages();
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("page", page);
        model.addAttribute("articles", page.getContent());
        model.addAttribute("tags", tags);
        return "home";
    }

    @GetMapping(value = "/tags")
    public String tags(Model model) {
        List<Tag> tags = tagService.findAllByCreateTime();
        model.addAttribute("tags", tags);
        model.addAttribute("tagsCount", tags.size());
        return "tags";
    }

    @GetMapping(value = "/code")
    public String code(Model model) {
        List<GitHubRepository> githubRepos = GitHub.getRepositories(GitHub.UPDATED);
        model.addAttribute("githubRepos", githubRepos);
        return "code";
    }

    @GetMapping(value = "/about")
    public String about(Model model) {
        return "about";
    }
}
