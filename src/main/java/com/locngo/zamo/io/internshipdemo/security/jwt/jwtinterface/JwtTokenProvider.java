package com.locngo.zamo.io.internshipdemo.security.jwt.jwtinterface;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenProvider extends SecurityConfigConst {

    public String createToken(String username);
    public Authentication getAuthentication(String token);
    public String getUsername(String token);
    public String resolveToken(HttpServletRequest request);
    public boolean validateToken(String token);
    public String getBodyFromToken(String token);
    public String resolveTokenFromHeader(String header);
}
