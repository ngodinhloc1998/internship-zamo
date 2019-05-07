package com.locngo.zamo.io.internshipdemo.security.jwt.impl;

import com.locngo.zamo.io.internshipdemo.model.usersecurity.MyUserPrinciple;
import com.locngo.zamo.io.internshipdemo.security.jwt.jwtinterface.JwtTokenProvider;
import com.locngo.zamo.io.internshipdemo.service.userapplication.impl.UserApplicationServiceImpl;
import com.locngo.zamo.io.internshipdemo.service.userapplication.service.UserApplicationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@ComponentScan(basePackageClasses = {MyUserPrinciple.class, UserApplicationServiceImpl.class})
public class JwtTokenProviderImpl implements JwtTokenProvider{

    private String secretKey = SECRET_KEY;
    private Long validityInMilliseconds = VALIDITY_IN_MILLISECONDS;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private MyUserPrinciple myUserPrinciple;

    @PostConstruct
    private void init(){
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public JwtTokenProviderImpl() {
    }

    private Date getValidity(){
        Date now = new Date();
        return new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);
    }

    private Claims getClaims(String username){

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("Authorization",
                userApplicationService.getRolesByUsername(username)
                        .stream().map(
                        role -> new SimpleGrantedAuthority(role.getAuthority())
                ).filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );

        return claims;
    }

    @Override
    public String createToken(String username) {
        return Jwts.builder()
                .setClaims(getClaims(username))
                .setIssuedAt(new Date())
                .setExpiration(getValidity())
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserPrinciple.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new RuntimeException("This token is invalid");
        }
    }

    @Override
    public String getBodyFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parse(token).getBody().toString();
    }


}
