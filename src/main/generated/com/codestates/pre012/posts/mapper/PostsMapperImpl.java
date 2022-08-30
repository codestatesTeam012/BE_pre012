package com.codestates.pre012.posts.mapper;

import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T00:00:16+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class PostsMapperImpl implements PostsMapper {

    @Override
    public Posts postsPostDtoToPosts(PostsDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Posts posts = new Posts();

        posts.setTitle( requestBody.getTitle() );
        posts.setContent( requestBody.getContent() );

        return posts;
    }

    @Override
    public Posts postsPatchDtoToPosts(PostsDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Posts posts = new Posts();

        posts.setPostsId( requestBody.getPostsId() );
        posts.setTitle( requestBody.getTitle() );
        posts.setContent( requestBody.getContent() );

        return posts;
    }

    @Override
    public PostsDto.Response postsToPostsDtoResponse(Posts posts) {
        if ( posts == null ) {
            return null;
        }

        PostsDto.Response.ResponseBuilder response = PostsDto.Response.builder();

        response.postsId( posts.getPostsId() );
        response.title( posts.getTitle() );
        response.content( posts.getContent() );

        return response.build();
    }

    @Override
    public List<PostsDto.Response> postsToPostsDtoResponses(List<Posts> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostsDto.Response> list = new ArrayList<PostsDto.Response>( posts.size() );
        for ( Posts posts1 : posts ) {
            list.add( postsToPostsDtoResponse( posts1 ) );
        }

        return list;
    }
}
