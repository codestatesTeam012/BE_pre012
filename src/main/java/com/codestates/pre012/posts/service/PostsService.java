package com.codestates.pre012.posts.service;

import com.codestates.pre012.exception.BusinessLogicException;
import com.codestates.pre012.exception.ExceptionCode;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;

import com.codestates.pre012.reply.repository.ReplyRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostsService {

    private final PostsRepository postsRepository;

    private final ReplyRepository replyRepository;

    public PostsService(PostsRepository postsRepository, ReplyRepository replyRepository) {
        this.postsRepository = postsRepository;
        this.replyRepository = replyRepository;
    }


    public Posts savedPosts(Posts postsPost, Member member) {

        postsPost.setMember(member);
        return postsRepository.save(postsPost);
    }

    //member 식별 이후 수정 가능하도록 멤버변수에 member 추가
    public Posts updatePosts(Posts patchPost ,Member member) {
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


    //작성자와 member가 동일한지 확인 후 삭제
    public void deletePosts(long postId, Member member) {

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
