package com.locngo.zamo.io.internshipdemo.controller;

import com.locngo.zamo.io.internshipdemo.model.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.RoleApplication;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationUserController {

    @Autowired
    @Qualifier("UserApplicationServiceImpl")
    private UserApplicationService userApplicationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = {"/apis/users"}, method = RequestMethod.POST)
    public @ResponseBody
    UserApplication createUser(@RequestBody UserApplication userApplication){
        try {
            return userApplicationService.createNewUser(userApplication);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = {"/apis/users/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    UserApplication getUserById(@PathVariable("id") Long id){
        try{
            return userApplicationService.getUser(id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = {"/apis/users"}, method = RequestMethod.GET)
    public @ResponseBody
    List<UserApplication> getUsers(){
        try{
            return userApplicationService.getUsers();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = {"/apis/users"},method = RequestMethod.PUT)
    public @ResponseBody
    UserApplication updateApplicationUser(@RequestBody UserApplication userApplication){
        try{
            return userApplicationService.updateUser(userApplication);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = "/apis/users/grant-privilege", method = RequestMethod.POST)
    public @ResponseBody
    UserApplication grantPrivilege(@RequestBody Map<String,String> args){
        return userApplicationService.addNewRole(args.get("username"),args.get("roleName"));
    }

    @RequestMapping(value = "/apis/users/roles/{username}",method = RequestMethod.GET)
    public @ResponseBody
    List<RoleApplication> getUserRoles(@PathVariable("username") String username){
        return userApplicationService.getRolesByUsername(username);
    }

    @RequestMapping(value = "/apis/users/signin",method = RequestMethod.POST)
    public @ResponseBody
    String signin(@RequestBody Map<String,String> userInfo){
        String username = userInfo.get("username");
        String password = userInfo.get("password");
        printBean();
        return userApplicationService.signin(username,password);
    }


    /**
     * print all Bean
     */

    @Autowired
    ApplicationContext applicationContext;

    public void printBean(){
        List listBean = Arrays.asList(applicationContext.getBeanDefinitionNames());
        for(Object bean: listBean){
            System.out.println(bean);
        }
    }

}