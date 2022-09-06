package com.codestates.pre012.reply.dto;


import com.codestates.pre012.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class ReplyDto {


    @Getter
    public static class Post {

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String content;
    }

    @Getter
    public static class Patch {

        private String content;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long replyId;
        private String username; // --> member 객체에서 뽑기
        private String content;

    }
}
