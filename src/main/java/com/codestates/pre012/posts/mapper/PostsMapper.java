package com.codestates.pre012.posts.mapper;


import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.reply.dto.ReplyDto;
import com.codestates.pre012.tag.dto.TagDto;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.Tag_Posts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostsMapper {

    Posts postsPostDtoToPosts(PostsDto.Post requestBody);
    Posts postsPatchDtoToPosts(PostsDto.Patch requestBody);

    //mapper 수동 구현
    default PostsDto.Response postsToPostsDtoResponse(Posts posts) { //reply를 posts에 넣기 위해 추가(reply -> replyResponseDto)
        List<ReplyDto.Response> replies= posts.getReplies().stream()
                .map(reply -> {
                    return ReplyDto.Response.builder()
                            .replyId(reply.getReplyId())
                            .postsId(reply.getPosts().getPostsId())
                            .content(reply.getContent())
                            .username(reply.getMember().getUsername())
                            .build();
                }).collect(Collectors.toList());
        List<Tag> tag = posts.getTag_posts()
                .stream()
                .map(Tag_Posts::getTag).collect(Collectors.toList());
        List<Tag.TagList> tagResponse = tag.stream()
                .map(Tag::getTagList).collect(Collectors.toList());

        PostsDto.Response response= PostsDto.Response.builder()
                .postsId(posts.getPostsId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .replies(replies)
                .tagResponse(tagResponse)
                .build();

        return response;
    }
    PostsDto.PostPageResponse postsToPostsPageResponse(Posts posts);

    List<PostsDto.PostPageResponse> postsToPostsDtoPostPageResponses(List<Posts> posts);



}
