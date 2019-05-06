package com.locngo.zamo.io.internshipdemo.security.sercurityinterface;

import com.locngo.zamo.io.internshipdemo.service.serviceinterface.UserApplicationService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenProvider extends SecurityConfigConst {

    public String createToken(String username);
    public Authentication getAuthentication(String token);
    public String getUsername(String token);
    public String resolveToken(HttpServletRequest request);
    public boolean validateToken(String token);
}
