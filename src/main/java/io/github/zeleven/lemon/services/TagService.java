package io.github.zeleven.lemon.services;

import io.github.zeleven.lemon.entities.Article;
import io.github.zeleven.lemon.entities.Tag;
import io.github.zeleven.lemon.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag findOne(Integer id) {
        return tagRepository.findOne(id);
    }

    public List<Tag> findAllByCreateTime() {
        return tagRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
    }

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteTag(Integer id) {
        Tag tag = findOne(id);
        List<Article> articles = tag.getArticles();
        for (int i = 0; i < articles.size(); i++) {
            articles.get(i).getTags().remove(tag);
        }
        tagRepository.delete(tag);
    }
}
