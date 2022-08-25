package com.codestates.pre012.member.service;


import com.codestates.pre012.member.dto.LoginMemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//memberservice 추가, repository 의존성 주입
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //member 회원가입
    public Member saveMember(Member member) {
        System.out.println(member.getEmail() +"/"+ member.getPassword());
        verifiedMemberEmail(member.getEmail());

        return memberRepository.save(member);
    }
    public Member login(LoginMemberDto loginMemberDto) {
        Member member = loginVerifiedEmail(loginMemberDto.getEmail());
        if(member.getPassword().equals(loginMemberDto.getPassword())) {
            return member;
        }
        else {
            throw new RuntimeException("wrong password!!");
        }
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

    public Member findMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() ->new RuntimeException("member not exist"));
        return findMember;
    }

}
