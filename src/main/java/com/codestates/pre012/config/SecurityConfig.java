package com.codestates.pre012.config;

import com.codestates.pre012.filter.JwtAuthenticationFilter;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.oauth.CustomOAuth2UserService;
import com.codestates.pre012.oauth.OAuth2SuccessHandler;
//import com.codestates.pre012.oauth.CustomOAuth2UserService;
//import com.codestates.pre012.oauth.OAuth2SuccessHandler;
import com.codestates.pre012.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final MemberService memberService;
    private final OAuth2SuccessHandler successHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.addFilterBefore(new FirstFilter(), BasicAuthenticationFilter.class); //추가
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable();

        http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/v1/member/create", "/v1/member/login","/v1/member/google/login").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/posts/**").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/posts/create")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST,"/v1/posts/patch")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE,"/v1/posts/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, memberService),
                        UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login()
                .defaultSuccessUrl("/v1/posts?page=1&size=10")
                .successHandler(successHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http
                .logout()
                .logoutSuccessUrl("/v1/posts?page=1&size=10");

        return http.build();
    }

}
