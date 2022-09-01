package com.codestates.pre012.tag.service;

import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.Tag_Posts;
import com.codestates.pre012.tag.repository.TagRepository;
import com.codestates.pre012.tag.repository.Tag_PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Tag_PostsService {

    private final Tag_PostsRepository tag_postsRepository;

    public Tag_Posts saveTagPost(Tag tag, Posts posts) {
        Tag_Posts tagPosts = Tag_Posts.builder()
                .posts(posts)
                .tag(tag)
                .build();
        return tag_postsRepository.save(tagPosts);
    }

    public List<Tag> findTagByPostsId(long postsId) {
        return tag_postsRepository.findByPostsId(postsId);
    }

}
