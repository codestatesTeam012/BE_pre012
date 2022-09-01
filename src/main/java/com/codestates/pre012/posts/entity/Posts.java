package com.codestates.pre012.posts.entity;


import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.reply.entity.Reply;
import com.codestates.pre012.tag.entity.Tag_Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postsId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @JoinColumn(name = "memberId")
    @ManyToOne
    private Member member;



    //page로 댓글을 받지 못해서 일단 임시방편으로 BatchSize로 댓글 100개만 불러올 수 있도록 설정
    //cascadetype.PERSIST : post저장시 댓글도 같이 저장, REMOVE : post 삭제시 댓글다 같이 삭제
    //orphanremoval 고아객체 삭제 https://dev-elop.tistory.com/entry/JPA-orphanRemoval-%EC%9A%A9%EB%8F%84
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "posts",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            orphanRemoval = true) //posts삭제시 reply도 같이 삭제
    @OrderBy("posts_id desc")
    private List<Reply> replies = new ArrayList<>();

    //순환 참조방지에 별 도움이 안되는 것 같아 addReplies() 삭제

    @Column(name = "view")
    private int view;

    private String tags;

    public List<String> tagList(String tag) {
        List<String> list = Arrays.asList(tag.split(" , "));
        return list;
    }

    @OneToMany(mappedBy = "posts", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Tag_Posts> tag_posts;



    public Posts(long postsId, String title, String content, int view) {
        this.postsId = postsId;
        this.title = title;
        this.content = content;
        this.view = view;
    }

    public Posts(long postsId, String title, String content) {
        this.postsId = postsId;
        this.title = title;
        this.content = content;
    }

}
