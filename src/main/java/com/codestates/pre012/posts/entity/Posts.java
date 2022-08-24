package com.codestates.pre012.posts.entity;

import com.codestates.pre012.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob //긴 문자열 저장을 위해 추가했습니다.
    @Column(name = "content", nullable = false)
    private String content;

    @JoinColumn(name = "memberId",nullable = false)
    @ManyToOne(fetch = FetchType.EAGER) //N+1문제 발생을 막기위해 추가했습니다.
    private Member member;

    //추가
    public void setMember(Member member) {
        this.member = member;
        if(!member.getPosts().contains(this)) {
            member.getPosts().add(this);
        }
    }
}
