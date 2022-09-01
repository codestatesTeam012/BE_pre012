package com.codestates.pre012.posts.service;

import com.codestates.pre012.exception.BusinessLogicException;
import com.codestates.pre012.exception.ExceptionCode;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;

import com.codestates.pre012.reply.repository.ReplyRepository;

import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.Tag_Posts;
import com.codestates.pre012.tag.service.TagService;
import com.codestates.pre012.tag.service.Tag_PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostsService {

    private final PostsRepository postsRepository;

    private final ReplyRepository replyRepository;
    private final Tag_PostsService tag_postsService;
    private final TagService tagService;

    public PostsService(PostsRepository postsRepository, ReplyRepository replyRepository, Tag_PostsService tag_postsService, TagService tagService) {
        this.postsRepository = postsRepository;
        this.replyRepository = replyRepository;
        this.tag_postsService = tag_postsService;
        this.tagService = tagService;
    }


    public Posts savedPosts(Posts postsPost, Member member, List<String> tag) {


        postsPost.setMember(member);
        List<Tag> tagList = tagService.saveTag(tag);
        List<Tag_Posts> tag_posts = new ArrayList<>();
        for(int i = 0; i< tagList.size(); i++) {
            tag_posts.add(tag_postsService.saveTagPost(tagList.get(i), postsPost));
        }
        postsPost.setTag_posts(tag_posts);
        return postsRepository.save(postsPost);
    }

    //member 식별 이후 수정 가능하도록 멤버변수에 member 추가
    public Posts updatePosts(Posts patchPost ,Member member, List<String> tag) {
        Posts findPosts = existPosts(patchPost.getPostsId());
        if(!findPosts.getMember().equals(member)) throw new RuntimeException("자신의 글만 수정 가능합니다.");

        Optional.ofNullable(patchPost.getTitle())
                .ifPresent(findPosts::setTitle);
        Optional.ofNullable(patchPost.getContent())
                .ifPresent(findPosts::setContent);

        //날로 쳐먹는 코딩
        List<Tag> tagList = tagService.saveTag(tag);
        List<Tag_Posts> tag_posts = new ArrayList<>();
        for(int i = 0; i< tagList.size(); i++) {
            tag_posts.add(tag_postsService.saveTagPost(tagList.get(i), patchPost));
        }
        findPosts.setTag_posts(tag_posts);



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
