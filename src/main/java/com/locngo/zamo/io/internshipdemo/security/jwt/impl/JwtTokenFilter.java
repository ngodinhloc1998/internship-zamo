package com.locngo.zamo.io.internshipdemo.security.jwt.impl;

import com.locngo.zamo.io.internshipdemo.exception.jwt.InvalidTokenException;
import com.locngo.zamo.io.internshipdemo.security.config.WebConfigureSecurity;
import com.locngo.zamo.io.internshipdemo.security.jwt.jwtinterface.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ComponentScan(basePackageClasses = {WebConfigureSecurity.class})
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(jwtTokenProvider == null){
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,httpServletRequest.getSession().getServletContext());
        }
        System.out.println(jwtTokenProvider);
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        System.out.println("Value of token is " + token);
        try{
            if(token != null && jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Body Token is " + jwtTokenProvider.getBodyFromToken(token));
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            System.out.println("Expired or invalid token!");
            return;
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
