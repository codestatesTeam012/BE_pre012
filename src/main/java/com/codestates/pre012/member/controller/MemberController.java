package com.codestates.pre012.member.controller;

import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.member.dto.MemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.mapper.MemberMapper;
import com.codestates.pre012.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    /**
     * 회원 관리 ( 회원 가입, 로그인 )
     */
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid MemberDto.Post postMember) {
        System.out.println("# email : "+postMember.getEmail());
        Member member = mapper.memberPostDtoToMember(postMember);
        System.out.println("# email : "+member.getEmail());
        Member createdMember = memberService.saveMember(member);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)) ,HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberDto.Login loginMember) {


        Member member = mapper.memberLoginDtoToMember(loginMember);
        Member loginMembers = memberService.login(member);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(loginMembers)),HttpStatus.OK);
    }


}
