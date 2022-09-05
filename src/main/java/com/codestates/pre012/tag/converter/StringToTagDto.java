package com.codestates.pre012.tag.converter;

import com.codestates.pre012.exception.BusinessLogicException;
import com.codestates.pre012.exception.ExceptionCode;
import com.codestates.pre012.tag.dto.TagDto;
import com.codestates.pre012.tag.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToTagDto {

    //string -> tagList

    public List<TagDto.Post> tagListToTagDtoResponse(List<String> tag) {
        List<Tag.TagList> tagList = stringToTagList(tag);
        List<TagDto.Post> tagResponse = tagList.stream()
                .map(t -> {
                    TagDto.Post response = TagDto.Post.builder()
                            .tagList(t).build();
                    return response;
                }).collect(Collectors.toList());
        return tagResponse;
    }

    private List<Tag.TagList> stringToTagList(List<String> tag) {
        return tag.stream().sorted()
                .map(t -> {
                    t = t.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z]","");
                    t = t.toUpperCase();
                    try{
                        return Tag.TagList.valueOf(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    throw new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND);
                }).distinct().collect(Collectors.toList());
    }

    public Tag.TagList StringToTagList(String tag) {
        tag = tag.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z]","");
        tag = tag.toUpperCase();
        try{
            return Tag.TagList.valueOf(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
