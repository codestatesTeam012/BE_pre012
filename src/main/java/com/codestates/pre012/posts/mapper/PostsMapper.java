package com.codestates.pre012.posts.mapper;


import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
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
    PostsDto.SearchResponse postsToSearchResponse(Posts posts, List<Tag> tags, String username);
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
