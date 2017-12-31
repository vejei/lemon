package io.github.zeleven.lemon.controllers.admin;

import com.google.gson.Gson;
import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.entities.Tag;
import io.github.zeleven.lemon.services.ArticleService;
import io.github.zeleven.lemon.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/article")
public class ArticleAdminController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

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
    public ResponseEntity<Object> updateArticle(@RequestBody Map<String, Object> postData) {
        Integer articleId = Integer.parseInt((String) postData.get("id"));
        String title = (String) postData.get("title");
        String content = (String) postData.get("content");
        Gson gson = new Gson();
        int[] tagIdList = gson.fromJson(postData.get("tags") + "", int[].class);

        Map<String, String> result = new HashMap<>();
        Article article = articleService.findOne(articleId);
        if (article != null) {
            article.setTitle(title);
            article.setContent(content);
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < tagIdList.length; i++) {
                Tag tag = tagService.findOne(tagIdList[i]);
                tags.add(tag);
            }
            article.setTags(tags);
            articleService.saveArticle(article);
            result.put("msg", "文章已更新");
        } else {
            result.put("msg", "文章不存在");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
