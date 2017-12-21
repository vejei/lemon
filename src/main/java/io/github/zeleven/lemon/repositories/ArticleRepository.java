package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("articleRepository")
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Page<Article> findAll(Pageable pageable);
}
