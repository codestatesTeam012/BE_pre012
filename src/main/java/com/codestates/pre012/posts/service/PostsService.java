package com.codestates.pre012.posts.service;

import com.codestates.pre012.exception.BusinessLogicException;
import com.codestates.pre012.exception.ExceptionCode;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;

import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.TagPosts;
import com.codestates.pre012.tag.service.TagPostService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    private final TagPostService tagPostService;


    public Posts savedPosts(Posts postsPost, Member member, List<Tag> tag) {

        postsPost.setMember(member);
        Posts posts = postsRepository.save(postsPost);

        if(tag != null && tag.size() != 0) {
            if(tag.size()>3) throw new BusinessLogicException(ExceptionCode.ONLY_WRITE_UP_TO_THREE);
            List<TagPosts> list = new ArrayList<>();
            for (Tag value : tag) {
                list.add(tagPostService.saveTagPosts(posts, new TagPosts(), value));
                posts.setTagPosts(list);
            }
        } else throw new BusinessLogicException(ExceptionCode.THERE_MUST_BE_AT_LEAST_ONE_TAG);
        //만약 태그 작성x 시 태그는 최소 1개 이상 작성해야 한다.
        return postsRepository.save(posts);
    }

    public Posts updatePosts(Long postsId,Posts posts ,Member member, List<Tag> tags) {
        Posts findPosts = existPosts(postsId);

        if(!findPosts.getMember().getPassword().equals(member.getPassword())) throw new RuntimeException("자신의 글만 수정 가능합니다.");

        Optional.ofNullable(posts.getTitle())
                .ifPresent(findPosts::setTitle);
        Optional.ofNullable(posts.getContent())
                .ifPresent(findPosts::setContent);

        //태그가 null인 경우 수정 전의 tag유지, tags존재시 변경
        if (tags != null && !tags.isEmpty()) {
            List<TagPosts> tagPostsList = findPosts.getTagPosts();
            for(TagPosts t : tagPostsList) {
                tagPostService.deleteTagPosts(t.getTagPostsId());
            }
            tagPostsList = new ArrayList<>();
            for (Tag tag : tags) {
                tagPostsList.add(tagPostService.saveTagPosts(findPosts, new TagPosts(), tag));
            }
            findPosts.setTagPosts(tagPostsList);
            return postsRepository.save(findPosts);
        }
        return postsRepository.save(findPosts);


    }

    public Posts lookPosts(long postId) {
        Posts posts = existPosts(postId);
        String username = posts.getMember().getUsername();
        System.out.println("==============================================" + username);

        int count = postsRepository.updateView(postId);
        return posts;
    }

    public Page<Posts> findAllPosts(int page, int size) {
        return postsRepository.findAll(PageRequest.of(page, size, Sort.by("postsId").descending()));
    }


    public void deletePosts(long postId, Member member) {
        System.out.println("delete service start");
        Posts findPosts = existPosts(postId);
        List<TagPosts> tagPosts = findPosts.getTagPosts();
        for(TagPosts t : tagPosts) {
            tagPostService.deleteTagPosts(t.getTagPostsId());
        }

        System.out.println("delete repository");
        if(findPosts.getMember().getPassword().equals(member.getPassword())) {
            postsRepository.delete(findPosts);
        }
        else throw new RuntimeException("자신의 게시글만 삭제 가능합니다.");
    }


    private Posts existPosts(long postsId) {

        Optional<Posts> existPosts = postsRepository.findById(postsId);

        Posts posts = existPosts.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POSTS_NOT_FOUND));
        return posts;

    }


}
