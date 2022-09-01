package com.codestates.pre012.tag.dto;

import com.codestates.pre012.reply.dto.ReplyDto;
import com.codestates.pre012.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class TagDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        private List<String> tagList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private Tag.TagList tagLists;

    }


}
