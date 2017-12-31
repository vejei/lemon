package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tagRepository")
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
