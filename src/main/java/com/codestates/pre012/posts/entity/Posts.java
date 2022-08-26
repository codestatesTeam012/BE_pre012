package com.codestates.pre012.posts.entity;

import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public Posts(long postsId, String title, String content) {
        this.postsId = postsId;
        this.title = title;
        this.content = content;
    }
}
