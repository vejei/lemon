package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
