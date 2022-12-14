package com.codestates.pre012.config.filter;

public interface JwtProperties {
	String SECRET = "BE012"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 6000000; // 100분

	int REFRESH_EXPIRATION_TIME = 60*1000*60*24;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
