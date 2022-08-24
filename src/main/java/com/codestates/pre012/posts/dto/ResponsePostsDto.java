package com.codestates.pre012.posts.dto;

import com.codestates.pre012.member.dto.ResponseMemberDto;
import com.codestates.pre012.member.entity.Member;
import lombok.*;


@Getter
@Setter
@Builder
public class ResponsePostsDto {

    private Long postId;

    private String title;

    private String content;

    private ResponseMemberDto memberDto;
    //post 조회시 member 정보도 같이 조회해야 할 것 같아 추가했습니다.
    //password의 response를 막기 위해 member대신 ResponseMemberDto를 ResponsePostsDto에 추가하였습니다.


}
