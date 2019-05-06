package com.locngo.zamo.io.internshipdemo.security.sercurityinterface;

public interface SecurityConfigConst {
    public static final String SECRET_KEY = "@zamo.io";
    public static final long VALIDITY_IN_MILLISECONDS = 3600000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "apis/users/sign-up";
}
