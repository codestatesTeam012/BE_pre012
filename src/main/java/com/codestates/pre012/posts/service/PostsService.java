package com.codestates.pre012.posts.service;

import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.repository.MemberRepository;
import com.codestates.pre012.posts.OrderBy;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

//postsservice 추가, repository 의존성 주입
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    public PostsService(PostsRepository postsRepository, MemberRepository memberRepository) {
        this.postsRepository = postsRepository;
        this.memberRepository = memberRepository;
    }
    //posts 생성
    public Posts savedPosts(Posts posts, Long memberId) {
        System.out.println(posts.getPostId());
        Member member = verifiedMember(memberId); //member 확인
        posts.setMember(member);

        return postsRepository.save(posts);
    }
    //posts 수정
    public Posts updatePosts(Posts posts,Long postsId ,Long memberId) {
//        verifiedMember(memberId); //member 확인
//        Posts findPosts = findByPostId(postsId); //post 식별
        Optional<Posts> post = postsRepository.find(postsId,memberId); //post 식별
        Posts findPosts = post.orElseThrow(() -> new RuntimeException("???????"));

        Optional.ofNullable(posts.getTitle()).ifPresent(title -> findPosts.setTitle(title));
        Optional.ofNullable(posts.getContent()).ifPresent(content -> findPosts.setContent(content));

        return postsRepository.save(findPosts);
    }
    //posts 조회
    public Posts findByPostId(Long postId) {
        Optional<Posts> findPosts = postsRepository.findById(postId);

        return findPosts.orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));
    }

    //나열 방법을 선택하면 선택한 방법의 순서대로 나열된 페이지 리턴
    public Page<Posts> findAllPosts(int page, int size, OrderBy orderBy){//정렬 방법(OLD/NEWEST/ALPHABETICAL)
        Pageable pageable;

        if(orderBy == OrderBy.OLD) {
            pageable = PageRequest.of(page, size, Sort.Direction.ASC, "postId");
        } else if (orderBy == OrderBy.NEWEST) {
            pageable = PageRequest.of(page, size, Sort.Direction.DESC, "postId");
        } else if (orderBy == OrderBy.ALPHABETICAL) {
            pageable = PageRequest.of(page, size, Sort.Direction.ASC,"title");
        } else throw new RuntimeException("잘못된 나열방법입니다.");

        Page<Posts> postsPage = postsRepository.findAll(pageable);
        return postsPage;
    }
    //posts 삭제
    public void deletePosts(Long postId) {
        findByPostId(postId);

        Posts posts = findByPostId(postId);
        postsRepository.delete(posts);
    }

    private Member verifiedMember(Long memberId) { //member 식별을 위해 추가
        Optional<Member> verifiedMember = memberRepository.findById(memberId);
        Member member = verifiedMember.orElseThrow(() ->new RuntimeException("please login"));
        return member;
    }

}
