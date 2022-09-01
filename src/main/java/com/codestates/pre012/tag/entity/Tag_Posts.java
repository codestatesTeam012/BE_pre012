package com.codestates.pre012.tag.entity;

import com.codestates.pre012.posts.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
public class Tag_Posts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tag_postsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tagId")
    private Tag tag;

    //setTag 삭제

    @ManyToOne
    @JoinColumn(name = "postId")
    private Posts posts;

    //setPosts()삭제



}
