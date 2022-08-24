package com.codestates.pre012.posts.mapper;

import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostsMapper {

    Posts postsPostDtoToPosts(PostsDto.Post requestBody);
    Posts postsPatchDtoToPosts(PostsDto.Patch requestBody);
    PostsDto.Response postsToPostsDtoResponse(Posts posts);
    List<PostsDto.Response> postsToPostsDtoResponses(List<Posts> posts);

}
