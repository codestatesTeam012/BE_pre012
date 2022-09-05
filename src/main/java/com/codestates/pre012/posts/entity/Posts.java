package com.codestates.pre012.posts.entity;


import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.reply.entity.Reply;
import com.codestates.pre012.tag.entity.TagPosts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postsId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @JoinColumn(name = "memberId")
    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "posts", fetch = LAZY,cascade = CascadeType.REMOVE) //post 삭제시 reply도 삭제 해야 하므로 cascade remove 속성 추가
    private List<Reply> replies = new ArrayList<>();

    @Column
    private int view;

    @BatchSize(size = 100)//findAll사용시 N+1 문제 때문에 추가 https://brunch.co.kr/@jinyoungchoi95/2
    @OneToMany(mappedBy = "posts", fetch = EAGER)
    private List<TagPosts> tagPosts = new ArrayList<>();

}
