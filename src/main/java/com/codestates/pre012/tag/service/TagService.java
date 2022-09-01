package com.codestates.pre012.tag.service;

import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> saveTag(List<String> tag) {
        List<Tag> list = new ArrayList<>();
        for(int i = 0; i<tag.size();i++) {
            Tag tag1 = Tag.builder()
                    .tagList(Tag.TagList.valueOf(tag.get(i)))
                    .build();
            list.add(tag1);
        }
        return list;
    }







}
