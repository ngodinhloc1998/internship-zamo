package com.locngo.zamo.io.internshipdemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import com.locngo.zamo.io.internshipdemo.model.usercourse.UserCourse;
import com.locngo.zamo.io.internshipdemo.model.usersecurity.MyUserPrinciple;
import com.locngo.zamo.io.internshipdemo.service.userapplication.service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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
    private MyUserPrinciple myUserPrinciple;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/apis/users"}, method = RequestMethod.POST)
    public @ResponseBody
    UserApplication createUser(@RequestBody UserApplication userApplication){
        return userApplicationService.createNewUser(userApplication);
    }

    @RequestMapping(value = "/apis/users/signup",method = RequestMethod.POST)
    public @ResponseBody
    String signup(@RequestBody UserApplication userApplication){
        return userApplicationService.signup(userApplication);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = {"/apis/users/{id}"}, method = RequestMethod.GET)
    public @ResponseBody
    UserApplication getUserById(@PathVariable("id") Long id){
        try{
            return userApplicationService.getUser(id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
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

    @RequestMapping(value = "/apis/users/details/{username}",method = RequestMethod.GET)
    public @ResponseBody
    UserDetails getUserDetails(@PathVariable("username") String username){
       return myUserPrinciple.loadUserByUsername(username);
    }

    @RequestMapping(value = "/apis/users/names-role/{username}",method = RequestMethod.GET)
    public @ResponseBody
    List<String> getNamesRole(@PathVariable String username){
        return userApplicationService.getNamesRoleAppicationByUsername(username);
    }


    @RequestMapping(value = "/apis/users/add-course",method = RequestMethod.POST)
    public @ResponseBody
    List<Course> addCourse(@RequestBody Map<String,String> userCourse,@RequestHeader("Authorization") String header){
        String username = userCourse.get("username");
        String courseName = userCourse.get("courseName");
        return userApplicationService.addCourse(username,courseName,header);
    }

    @RequestMapping(value = "/apis/users/{username}/courses",method = RequestMethod.GET)
    public List<Course> getCourse(@PathVariable("username") String username,@RequestHeader("Authorization") String header){
        return userApplicationService.getCourses(username,header);
    }

    @RequestMapping(value = "/apis/users/update-completed",method = RequestMethod.PUT)
    public @ResponseBody
    UserCourse updateAmountedCompleted(@RequestBody Map<String,Object> map,@RequestHeader("Authorization") String header){
        String username = (String)map.get("username");
        String courseName = (String)map.get("courseName");
        Long number = (Long) Long.parseLong(String.valueOf(map.get("amountCompleted")));
        return userApplicationService.updateAmountCompleted(username,courseName,number,header);
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