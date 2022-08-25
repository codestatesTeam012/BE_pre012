package com.codestates.pre012.member.service;

import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //member 회원가입
    public Member saveMember(Member member) {

        verifiedMemberEmail(member.getEmail());

        return memberRepository.save(member);


    }
    public Member login(Member member) {

        Member member1 = loginVerifiedEmail(member.getEmail());

        if(member1.getPassword().equals(member.getPassword())) {
            member.setMemberId(member1.getMemberId());
            return member;
        }
        else {
            throw new RuntimeException("wrong password!!");
        }
    }

    public Member findMember (long memberId) {
        return null;
    }

    public Page<Member> findMembers(int page, int size) {
        return null;
    }

    public void deleteMember(long memberId) {
    }



    //이메일 존재시 예외처리
    public void verifiedMemberEmail(String email) {
        Optional<Member> verifyMember = memberRepository.findByEmail(email);
        if(verifyMember.isPresent()) throw new RuntimeException("member Already Exist");
    }

    public Member loginVerifiedEmail(String email) {
        Optional<Member> loginMember = memberRepository.findByEmail(email);
        Member member = loginMember.orElseThrow(() -> new RuntimeException("email not exist"));

        return member;
    }
}