package com.codestates.pre012.tag.entity;

import com.codestates.pre012.posts.entity.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TagPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagPostsId;

    @ManyToOne
    @JoinColumn(name = "postsId")
    private Posts posts;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "tagId")
    private Tag tag;
}
