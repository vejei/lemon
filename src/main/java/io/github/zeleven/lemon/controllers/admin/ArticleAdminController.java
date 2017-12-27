package io.github.zeleven.lemon.controllers.admin;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/article")
public class ArticleAdminController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String create() {
        Article article = new Article();
        article.setTitle("无标题");
        articleService.saveArticle(article);
        return "redirect:/admin/article";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "aid_input") Integer aid) {
        articleService.deleteArticle(aid);
        return "redirect:/admin/article";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> updateArticle(@RequestBody Article a) {
        Map<String, String> result = new HashMap<>();
        Article article = articleService.findOne(a.getId());
        if (article != null) {
            article.setTitle(a.getTitle());
            article.setContent(a.getContent());
            articleService.saveArticle(article);
            result.put("msg", "文章已更新");
        } else {
            result.put("msg", "文章不存在");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
