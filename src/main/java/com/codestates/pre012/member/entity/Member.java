package com.codestates.pre012.member.entity;

import com.codestates.pre012.baseEntity.BaseEntity;
import com.codestates.pre012.posts.entity.Posts;
import lombok.*;

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

    //oauth 프로필
    private String picture;

    private String provider;

    private String providerId;

    public enum Role{
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN;
    }

    public Member(String email, Role role, String provider, String providerId) {
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member(String email, String picture, Role role) {
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}
