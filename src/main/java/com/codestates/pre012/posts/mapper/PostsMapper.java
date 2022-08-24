package com.codestates.pre012.posts.mapper;

import com.codestates.pre012.member.dto.ResponseMemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.posts.dto.PatchPostsDto;
import com.codestates.pre012.posts.dto.PostPostsDto;
import com.codestates.pre012.posts.dto.ResponsePostsDto;
import com.codestates.pre012.posts.entity.Posts;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

//mapper 어노테이션 추가
@Mapper(componentModel = "spring")
public interface PostsMapper {

    Posts postPostsDtoToPosts(PostPostsDto postPostsDto);

    Posts patchPostsDtoToPosts(PatchPostsDto patchPostsDto);

    default ResponsePostsDto postsToResponsePostsDto(Posts posts) {
        Long memberId = posts.getMember().getMemberId();
        String email = posts.getMember().getEmail();
        ResponseMemberDto responseMemberDto = ResponseMemberDto.builder()
                .memberId(memberId)
                .email(email)
                .build();

        ResponsePostsDto responsePostsDto = ResponsePostsDto.builder()
                .postId(posts.getPostId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .memberDto(responseMemberDto)
                .build();
        return responsePostsDto;

    }

    default List<ResponsePostsDto> postsToResponsePostsDto(List<Posts> postsList) {
        List<ResponsePostsDto> responsePostsDtos
                = postsList.stream()
                .map(posts -> postsToResponsePostsDto(posts))
                .collect(Collectors.toList());
        return responsePostsDtos;
    }
}
