package com.codestates.pre012.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
public class ResponseMemberDto {

    private Long memberId;

    private String email;

    //private String message;
}
