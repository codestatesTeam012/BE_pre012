package com.codestates.pre012.member.service;

import com.codestates.pre012.auth.PrincipalDetails;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //member 회원가입
    public Member saveMember(Member member) {

        verifiedMemberEmail(member.getEmail());
        member.setRole(Member.Role.ROLE_USER);

        return memberRepository.save(member);


    }
    public Member login(Member member) {

        Member member1 = loginVerifiedEmail(member.getEmail());

        if(passwordEncoder.matches(member.getPassword(), member1.getPassword())) {
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = loginVerifiedEmail(email);
        return new PrincipalDetails(member);
    }


}