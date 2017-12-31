package io.github.zeleven.lemon.services;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.repositories.ArticleRepository;
import io.github.zeleven.lemon.utils.MarkdownParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleService")
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public List<Article> findAllByCreateTime() {
        return articleRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
    }

    public Article findOne(Integer articleId) {
        return articleRepository.findOne(articleId);
    }

    public void deleteArticle(Integer articleId) {
        articleRepository.delete(articleId);
    }

    public void saveArticle(Article article) {
        if (article.getContent() != null) {
            article.setContentHtml(MarkdownParser.parse(article.getContent()));
        }
        articleRepository.save(article);
    }
}
