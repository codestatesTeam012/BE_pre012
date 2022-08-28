package com.codestates.pre012.member.entity;

import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.posts.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //@Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Posts> posts;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String provider;

    private String providerId;

    public enum Role{
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN;
    }


}
