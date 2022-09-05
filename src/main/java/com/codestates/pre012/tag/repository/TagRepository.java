package com.codestates.pre012.tag.repository;

import com.codestates.pre012.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagList(Tag.TagList tagList);

}
