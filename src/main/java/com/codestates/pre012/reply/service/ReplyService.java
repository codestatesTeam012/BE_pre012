package com.codestates.pre012.reply.service;

import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;
import com.codestates.pre012.reply.entity.Reply;
import com.codestates.pre012.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final PostsRepository postsRepository;

    public ReplyService(ReplyRepository replyRepository, PostsRepository postsRepository) {
        this.replyRepository = replyRepository;
        this.postsRepository = postsRepository;
    }

    public Reply createReply(Long postId, Member member, Reply reply) {
        // 게시글을 찾는다. --> id 를 뽑아 온다
        Posts posts = postsRepository.findById(postId).orElse(null);
        if (posts != null) {
            Long postsId = posts.getPostsId();
        }

        // 맴버를 찾는다. --> id 를 뽑아 온다.
        Long memberId = member.getId();

        // reply 객체에 넣어주기
        reply.setPosts(posts);
        reply.setMember(member);

        // 댓글 객체값을 DB 저장
        Reply createReply = replyRepository.save(reply);

        return createReply;
    }

    public Reply viewReply(Long replyId) {
        return replyRepository.findById(replyId).orElse(null);
    }
}