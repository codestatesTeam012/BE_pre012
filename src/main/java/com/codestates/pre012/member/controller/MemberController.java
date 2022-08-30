package com.codestates.pre012.member.controller;

import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.member.dto.MemberDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.mapper.MemberMapper;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/member")
@Validated
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 회원 관리 ( 회원 가입, 로그인 )
     */
    @PostMapping("/create")
    public ResponseEntity join(@Valid @RequestBody MemberDto.Post postMember) {

        Member member = mapper.memberPostDtoToMember(postMember);
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        Member createdMember = memberService.saveMember(member);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(createdMember)) ,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody MemberDto.Login loginMember, HttpServletResponse response) {
        Member member = mapper.memberLoginDtoToMember(loginMember);

        Member loginMembers = memberService.login(member);
        String accessToken = jwtTokenProvider.createToken(loginMembers.getEmail(), response);


        return new ResponseEntity<>(mapper.memberToMemberResponseDto(loginMembers), HttpStatus.OK);
    }

    //@PostMapping

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("cookie", null)
                .maxAge(0)
                .path("/")
                .secure(false)
                .httpOnly(true)
                .maxAge(0)
                .build();
        return  ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .header("accessToken","").build();
    }





}
