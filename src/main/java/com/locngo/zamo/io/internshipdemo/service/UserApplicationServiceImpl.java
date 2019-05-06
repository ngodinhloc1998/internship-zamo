package com.locngo.zamo.io.internshipdemo.service;

import com.locngo.zamo.io.internshipdemo.model.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.RoleApplication;
import com.locngo.zamo.io.internshipdemo.repository.RoleApplicationRepository;
import com.locngo.zamo.io.internshipdemo.repository.UserApplicationRepository;
import com.locngo.zamo.io.internshipdemo.security.sercurityinterface.JwtTokenProvider;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.UserApplicationService;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.RoleApplicationService;
import com.locngo.zamo.io.internshipdemo.service.serviceinterface.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Qualifier("UserApplicationServiceImpl")
public class UserApplicationServiceImpl implements UserApplicationService{

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
            userApplication.setPassword(bCryptPasswordEncoder.encode(userApplication.getPassword()));
            userApplication.getUserRole().add(userRoleService.createNewUserRole(userApplication.getUsername(),BaseRoleConst.ROLE_ADMIN));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    @Override
    public UserApplication updateUser(UserApplication userApplication) {
        if(userApplication != null && userApplicationRepository.existsUserApplicationByUsername(userApplication.getUsername())){
            userApplication.setId(userApplicationRepository.findUserApplicationsByUsername(userApplication.getUsername()).getId());
            userApplication.setPassword(bCryptPasswordEncoder.encode(userApplication.getPassword()));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    @Override
    public UserApplication addNewRole(String username, String roleName) {
        if(userApplicationRepository.existsUserApplicationByUsername(username) && roleApplicationRepository.existsRoleByName(roleName)) {
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
            password = bCryptPasswordEncoder.encode(password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return jwtTokenProvider.createToken(username);
        }catch (AuthenticationException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String signup(UserApplication userApplication) {
        userApplication = createNewUser(userApplication);
        return jwtTokenProvider.createToken(userApplication.getUsername());
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApplication userApplication = getUserByUserName(username);
        if(userApplication == null){
            throw new RuntimeException("User '" + username + "' not found!");
        }
        return User.withUsername(userApplication.getUsername())
                .password(userApplication.getPassword())
                .authorities(getRolesByUsername(userApplication.getUsername()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
