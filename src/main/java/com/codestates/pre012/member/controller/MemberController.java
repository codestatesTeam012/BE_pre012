package com.codestates.pre012.member.controller;

import com.codestates.pre012.member.dto.LoginMemberDto;
import com.codestates.pre012.member.dto.PostMemberDto;
import com.codestates.pre012.member.dto.ResponseMemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.mapper.MemberMapper;
import com.codestates.pre012.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping("/join")
    public ResponseEntity createMember(@RequestBody PostMemberDto postMemberDto) {
        System.out.println(postMemberDto.getEmail() + " : "+postMemberDto.getPassword());
        Member member = mapper.PostMemberDtoToMember(postMemberDto);
        System.out.println(member.getEmail() + " : "+member.getPassword());

        memberService.savedMember(member);

        return new ResponseEntity<>("회원가입 성공!",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginMember(@RequestBody LoginMemberDto loginMemberDto) {
        System.out.println("# loginMemberDto email : "+loginMemberDto.getEmail());
        Member member = memberService.login(loginMemberDto);
        System.out.println("# member email : "+ member.getEmail());

        return new ResponseEntity<>("로그인 성공!!",HttpStatus.OK);
    }


}
