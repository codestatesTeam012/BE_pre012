package com.codestates.pre012.posts.entity;


import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.likes.Like;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.reply.Reply;
import com.codestates.pre012.tag.Tag_Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "posts")
    private List<Like> likes = new ArrayList<>(); //좋야요 수 조회를 위해 양방향 설정
    public void addLike(Like like) { //양방향 관계 설정을 위해 참조
        likes.add(like);
        if(like.getPosts() != this) {  //순환 참조를 막기위해 추가
            like.setPosts(this);
        }
    }

    @OneToMany(mappedBy = "posts",
            cascade = CascadeType.ALL,
            orphanRemoval = true) //posts에 의해 tag의 생명주기가 달려있다고 생각이 들어서 cascade+orphanRemove 처리를 했습니다.
    private List<Tag_Posts> tag_postsList = new ArrayList<>(); //post에 tag조회를 위해 양방향 설정

    public void addTag_Posts(Tag_Posts tag_posts) { //양방향 관계 설정을 위해 참조 추가
        this.tag_postsList.add(tag_posts);
        if(tag_posts.getPosts() != this) { //순환참조 방지
           tag_posts.setPosts(this);
        }
    }

    @OneToMany(mappedBy = "posts",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            orphanRemoval = true) //posts삭제시 reply도 같이 삭제
    private List<Reply> replies = new ArrayList<>();

    public void addReplies(Reply reply) {
        this.replies.add(reply);
        if(reply.getPosts() != this) {
            reply.setPosts(this);
        }
    }

    @Column(name = "view")
    private int view;



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
    //todo : 특정 태그 선택시 select p from posts p join p.tag t로 tag관련된 posts 조회 가능해야 함
}
