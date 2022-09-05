package com.codestates.pre012.tag.dto;

import com.codestates.pre012.tag.entity.Tag;
import lombok.*;

public class TagDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post{
        private Tag.TagList tagList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Tag.TagList tagList;
    }


}
