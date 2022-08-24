package com.codestates.pre012.member.entity;

import com.codestates.pre012.posts.entity.Posts;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Posts> posts;

    /*
    * @JOIN~~~~
    *
    * */
}
