package com.codestates.pre012.posts.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class PostsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String title;

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String content;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        @Positive
        private long postsId;

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String title;

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String content;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private long postsId;
        private String title;
        private String content;

        public Response(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}