package com.locngo.zamo.io.internshipdemo;

import com.locngo.zamo.io.internshipdemo.security.sercurityinterface.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class InternshipDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternshipDemoApplication.class, args);
    }

}
