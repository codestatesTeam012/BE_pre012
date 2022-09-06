package com.codestates.pre012.posts.mapper;


import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.reply.dto.ReplyDto;
import com.codestates.pre012.tag.dto.TagDto;
import com.codestates.pre012.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostsMapper {

    Posts postsPostDtoToPosts(PostsDto.Post requestBody);

    PostsDto.PostsResponse postsToPostsResponse(Posts posts, List<Tag> tags);

    default PostsDto.SearchResponse postsToSearchResponse(Posts posts, List<Tag> tags, String username) {
        PostsDto.SearchResponse searchResponse = PostsDto.SearchResponse
                .builder()
                .postsId(posts.getPostsId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .username(posts.getMember().getUsername())
                .view(posts.getView())
                .tags(tags.stream().map(t -> t.getTagList()).collect(Collectors.toList())
                        .stream().map(tag -> TagDto.Response.builder().tagList(tag).build()).collect(Collectors.toList()))
                .replies(posts.getReplies().stream().map(r -> ReplyDto.Response.builder().replyId(r.getReplyId())
                        .content(r.getContent())
                        .username(r.getMember().getUsername()).build()).collect(Collectors.toList()))
                .build();
        return searchResponse;
    }

    default List<PostsDto.PostsResponse> postsToPostsDtoResponses(List<Posts> posts) {
        List<PostsDto.PostsResponse> list = posts.stream()
                .map(p -> PostsDto.PostsResponse.builder()
                        .postsId(p.getPostsId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .tags(p.getTagPosts().stream()
                                .map(tp -> tp.getTag()).collect(Collectors.toList())
                                .stream()
                                .map(t -> TagDto.Response.builder()
                                        .tagList(t.getTagList())
                                        .build()).collect(Collectors.toList())).build())
                .collect(Collectors.toList());
        return list;
        //이거 괜찮은거 맞나...?
    }

}
