package com.codestates.pre012.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static String secretKey = "jwt_token";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);


        if(token == null || !token.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String email = "";
        String jwtToken = token.replace("Bearer ","");
        try {
            email = JWT.require(Algorithm.HMAC512("jwt_token"))
                    .build()
                    .verify(jwtToken)
                    .getClaim("email").asString();
        } catch (JWTDecodeException e) {
            chain.doFilter(request, response);
        }


        if(email != null) {
            Member member = memberService.loginVerifiedEmail(email);
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);

    }
}
