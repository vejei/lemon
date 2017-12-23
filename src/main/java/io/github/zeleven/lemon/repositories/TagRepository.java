package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.entities.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("tagRepository")
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
