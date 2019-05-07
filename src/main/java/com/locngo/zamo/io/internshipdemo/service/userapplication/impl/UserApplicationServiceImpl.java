package com.locngo.zamo.io.internshipdemo.service.userapplication.impl;

import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import com.locngo.zamo.io.internshipdemo.repository.userapplication.RoleApplicationRepository;
import com.locngo.zamo.io.internshipdemo.repository.roleapplication.UserApplicationRepository;
import com.locngo.zamo.io.internshipdemo.security.jwt.jwtinterface.JwtTokenProvider;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.BaseRoleConst;
import com.locngo.zamo.io.internshipdemo.service.userapplication.service.UserApplicationService;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.RoleApplicationService;
import com.locngo.zamo.io.internshipdemo.service.roleapplication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Qualifier("UserApplicationServiceImpl")
public class UserApplicationServiceImpl implements UserApplicationService{

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("RoleApplicationServiceImpl")
    private RoleApplicationService roleApplicationService;

    @Autowired
    @Qualifier("UserRoleServiceImpl")
    private UserRoleService userRoleService;

    @Autowired
    private RoleApplicationRepository roleApplicationRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    @Qualifier("SecurityAuthenticationManage")
    private AuthenticationManager authenticationManager;

    @Override
    public UserApplication createNewUser(UserApplication userApplication) {
        if(userApplication != null){
            if (userApplicationRepository.findAll() != null
                || userApplicationRepository.findAll().size() > 0
            ){
                if(userApplicationRepository.existsUserApplicationByUsername(userApplication.getUsername())){
                    throw new RuntimeException("This user was exist!");
                }
            }
            userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
            userApplicationRepository.save(userApplication);
            userApplication.getUserRole().add(userRoleService.createNewUserRole(userApplication.getUsername(), BaseRoleConst.ROLE_USER));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    @Override
    public UserApplication updateUser(UserApplication userApplication) {
        if(userApplication != null && userApplicationRepository.existsUserApplicationByUsername(userApplication.getUsername())){
            userApplication.setId(userApplicationRepository.findUserApplicationsByUsername(userApplication.getUsername()).getId());
            userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    @Override
    public UserApplication addNewRole(String username, String roleName) {
        if(userApplicationRepository.existsUserApplicationByUsername(username) && roleApplicationService.existRoleApplicationByName(roleName)) {
            if (!getRolesByUsername(username).contains(roleApplicationService.getRoleByName(roleName))) {
                UserApplication userApplication = userApplicationRepository.findUserApplicationsByUsername(username);
                userApplication.getUserRole().add(userRoleService.createNewUserRole(username, roleName));
                return userApplicationRepository.save(userApplication);
            }
        }
        return null;
    }

    @Override
    public UserApplication getUser(Long id) {
        return userApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not exist"));
    }

    @Override
    public UserApplication getUserByUserName(String username) {
        UserApplication userApplication = userApplicationRepository.findUserApplicationsByUsername(username);
        if(userApplication != null){
            return userApplication;
        }
        return null;
    }

    @Override
    public List<UserApplication> getUsers() {
        List<UserApplication> userApplications = userApplicationRepository.findAll();
        if(userApplications != null){
            return userApplications;
        }
        return null;
    }

    @Override
    public String signin(String username, String password) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return jwtTokenProvider.createToken(username);
        }catch (AuthenticationException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String signup(UserApplication userApplication) {
        return jwtTokenProvider.createToken(createNewUser(userApplication).getUsername());
    }

    @Override
    public void signout(String username) {

    }

    @Override
    public List<RoleApplication> getRolesByUsername(String username) {
        if(userApplicationRepository.existsUserApplicationByUsername(username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<RoleApplication> roles = (List<RoleApplication>)entityManager.createQuery("SELECT r FROM RoleApplication r JOIN r.userRole rru JOIN rru.userApplication u " +
                    "WHERE u.username=:username").setParameter("username",username).getResultList();
            return roles;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userApplicationRepository.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userApplicationRepository.deleteUserApplicationByUsername(username);
    }

    @Override
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username);
    }

    @Override
    public String whoami(String token) {
        return jwtTokenProvider.getUsername(token);
    }

    @Override
    public boolean existUserApplicationByUsername(String username) {
        return userApplicationRepository.existsUserApplicationByUsername(username);
    }

    @Override
    public List<String> getNamesRoleAppicationByUsername(String username) {
        if(userApplicationRepository.existsUserApplicationByUsername(username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<String> namesRole = (List<String>) entityManager.createQuery(
                    "SELECT r.name FROM RoleApplication r JOIN r.userRole ur JOIN ur.userApplication u WHERE u.username = :username"
            ).setParameter("username",username).getResultList();
            return namesRole;
        }
        return null;
    }

    @Override
    public List<SimpleGrantedAuthority> getGrantedAuthorityByUsername(String username) {
        if(userApplicationRepository.existsUserApplicationByUsername(username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<RoleApplication> namesRole = (List<RoleApplication>) entityManager.createQuery(
                    "SELECT r FROM RoleApplication r JOIN r.userRole ur JOIN ur.userApplication u WHERE u.username = :username"
            ).setParameter("username",username).getResultList();

            List<SimpleGrantedAuthority> namesRoleToString = namesRole.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList());
            return namesRoleToString;
        }
        return null;
    }
}
