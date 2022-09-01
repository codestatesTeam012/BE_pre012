package com.codestates.pre012.posts.service;

import com.codestates.pre012.exception.BusinessLogicException;
import com.codestates.pre012.exception.ExceptionCode;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;
import com.codestates.pre012.reply.ReplyRepository;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.Tag_Posts;
import com.codestates.pre012.tag.repository.Tag_PostsRepository;
import com.codestates.pre012.tag.service.TagService;
import com.codestates.pre012.tag.service.Tag_PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    private final ReplyRepository replyRepository;
    private final TagService tagService;
    private final Tag_PostsService tag_postsService;
    private final Tag_PostsRepository tag_postsRepository;


    public Posts savedPosts(Posts postsPost, Member member) {

        postsPost.setMember(member);
        return postsRepository.save(postsPost);
    }

    public Posts updatePosts(Posts patchPost, Member member) {

        Posts findPosts = existPosts(patchPost.getPostsId());
        if(!findPosts.getMember().equals(member)) throw new RuntimeException("자신의 글만 수정 가능합니다.");

        Optional.ofNullable(patchPost.getTitle())
                .ifPresent(findPosts::setTitle);
        Optional.ofNullable(patchPost.getContent())
                .ifPresent(findPosts::setContent);

        return postsRepository.save(findPosts);
    }

    public Posts lookPosts(long postId) {
        Posts posts = postsRepository.findById(postId).orElse(null);

        int count = postsRepository.updateView(postId);

        return existPosts(postId);
    }

    public Page<Posts> findAllPosts(int page, int size) {
        return postsRepository.findAll(PageRequest.of(page, size, Sort.by("postsId").descending()));
    }



    public void deletePosts(long postId
                            , Member member) {

        Posts findPosts = existPosts(postId);
        if(findPosts.getMember().equals(member)) {
            postsRepository.delete(findPosts);
        }
        else throw new RuntimeException("자신의 게시글만 삭제 가능합니다.");
    }

    private Posts existPosts(long postsId) {

        Optional<Posts> existPosts = postsRepository.findById(postsId);

        return existPosts.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POSTS_NOT_FOUND));
    }
}
