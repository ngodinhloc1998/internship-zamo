package com.locngo.zamo.io.internshipdemo.security;

import com.locngo.zamo.io.internshipdemo.security.sercurityinterface.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ComponentScan(basePackageClasses = {JwtTokenProviderImpl.class})
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
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            throw new RuntimeException(e.getMessage());
        }finally {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }

}
