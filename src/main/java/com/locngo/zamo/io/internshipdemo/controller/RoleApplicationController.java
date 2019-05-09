package com.locngo.zamo.io.internshipdemo.controller;

import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.RoleApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleApplicationController {

    @Autowired
    @Qualifier("RoleApplicationServiceImpl")
    private RoleApplicationService roleApplicationService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/apis/roles", method = RequestMethod.POST)
    public @ResponseBody
    RoleApplication createRole(@RequestBody RoleApplication roleApplication){
        return roleApplicationService.createNewRole(roleApplication);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/apis/roles", method = RequestMethod.PUT)
    public @ResponseBody
    RoleApplication updateRole(@RequestBody RoleApplication roleApplication){
        return roleApplicationService.updateRole(roleApplication);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/apis/roles/{id}", method = RequestMethod.GET)
    public @ResponseBody
    RoleApplication getRole(@PathVariable("id") Long id){
        return roleApplicationService.getRole(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/apis/roles", method = RequestMethod.GET)
    public @ResponseBody
    List<RoleApplication> getRoles(){
        return roleApplicationService.getRoles();
    }
}
