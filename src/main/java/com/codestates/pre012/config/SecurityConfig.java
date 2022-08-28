package com.codestates.pre012.config;

import com.codestates.pre012.filter.JwtAuthenticationFilter;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.oauth.PrincipalOauth2UserService;
import com.codestates.pre012.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final MemberService memberService;

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.addFilterBefore(new FirstFilter(), BasicAuthenticationFilter.class); //추가
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/v1/posts?page=0&size=10")
                .and()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/v1/member/create", "/v1/member/login").permitAll()
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

        return http.build();
    }

//    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//            http.addFilter(corsFilter)
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberService));
//        }
//    }
}
