package com.codestates.pre012.member.repository;

import com.codestates.pre012.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

//    @Query("select m from Member m where m.email = :email")
//    Member findMember(@Param("email") String email);

}
