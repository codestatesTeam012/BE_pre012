package com.codestates.pre012.posts.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Validated
public class PostsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        @Positive
        private long postsId;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        @Positive
        private long postsId;
        @NotBlank
        private String title;
        @NotBlank
        private String content;

        public Response(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}