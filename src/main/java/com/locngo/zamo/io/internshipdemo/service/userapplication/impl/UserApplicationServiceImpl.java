package com.locngo.zamo.io.internshipdemo.service.userapplication.impl;

import com.locngo.zamo.io.internshipdemo.exception.course.CanNotFoundCourseException;
import com.locngo.zamo.io.internshipdemo.exception.roleapplication.RoleNotExistException;
import com.locngo.zamo.io.internshipdemo.exception.roleapplication.RoleWasExistedInUserException;
import com.locngo.zamo.io.internshipdemo.exception.userapplication.*;
import com.locngo.zamo.io.internshipdemo.exception.usercourse.CanNotFoundUserCourseException;
import com.locngo.zamo.io.internshipdemo.model.course.Course;
import com.locngo.zamo.io.internshipdemo.model.userapplication.UserApplication;
import com.locngo.zamo.io.internshipdemo.model.roleapplication.RoleApplication;
import com.locngo.zamo.io.internshipdemo.model.usercourse.UserCourse;
import com.locngo.zamo.io.internshipdemo.repository.course.CourseRepository;
import com.locngo.zamo.io.internshipdemo.repository.course.UserCourseRepository;
import com.locngo.zamo.io.internshipdemo.repository.userapplication.RoleApplicationRepository;
import com.locngo.zamo.io.internshipdemo.repository.roleapplication.UserApplicationRepository;
import com.locngo.zamo.io.internshipdemo.security.jwt.jwtinterface.JwtTokenProvider;
import com.locngo.zamo.io.internshipdemo.service.course.CourseService;
import com.locngo.zamo.io.internshipdemo.service.course.UserCourseService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    @Qualifier("SecurityAuthenticationManage")
    private AuthenticationManager authenticationManager;


    private boolean validateAccount(UserApplication userApplication){
        if(userApplication == null){
            throw new UserApplicationNullException();
        }
        if(userApplication.getUsername() == null || userApplication.getPassword() == null
        || userApplication.getEmail() == null || userApplication.getName() == null){
            throw new NotEnoughUserFieldException();
        }
        if(userApplication.getUsername().equals("")){
            throw new UsernameCanNotBeEmptyException();
        }
        if(userApplication.getPassword().equals("")){
            throw new PasswordCanNotBeEmptyException();
        }
        if(userApplication.getEmail().equals("")){
            throw new EmailCanNotBeEmptyException();
        }
        if (userApplication.getName().equals("")){
            throw new NameCanNotBeEmptyException();
        }
        if (userApplicationRepository.findAll() != null
                || userApplicationRepository.findAll().size() > 0
        ){
            if(userApplicationRepository.existsUserApplicationByUsername(userApplication.getUsername())){
                throw new UsernameWasExistException(userApplication.getUsername());
            }
        }
        return true;
    }

    private boolean validateUsername(String username){
        if(!userApplicationRepository.existsUserApplicationByUsername(username)){
            throw new CanNotFoundUserByUsernameException(username);
        }
        return true;
    }

    public boolean validateRoleName(String name){
        if(!roleApplicationRepository.existsRoleApplicationByName(name)){
            throw new RoleNotExistException(name);
        }
        return true;
    }

    private boolean checkRoleWasContainedByUser(String username,String roleName){
        if(getRolesByUsername(username).contains(roleApplicationService.getRoleByName(roleName))){
            throw new RoleWasExistedInUserException(username,roleName);
        }
        return true;
    }

    private boolean checkSelfOwner(String header,String username){
        String token = jwtTokenProvider.resolveTokenFromHeader(header);
        if(token != null && (jwtTokenProvider.getUsername(token).equals(username))){
            return true;
        }
        return false;
    }

    @Override
    public UserApplication createNewUser(UserApplication userApplication) {
        if(validateAccount(userApplication)){
            userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
            userApplicationRepository.save(userApplication);
            userApplication.getUserRole().add(userRoleService.createNewUserRole(userApplication.getUsername(), BaseRoleConst.ROLE_USER));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    //Done

    @Override
    public UserApplication updateUser(UserApplication userApplication) {
        if(validateAccount(userApplication)){
            userApplication.setId(userApplicationRepository.findUserApplicationsByUsername(userApplication.getUsername()).getId());
            userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
            return userApplicationRepository.save(userApplication);
        }
        return null;
    }

    @Override
    public UserApplication addNewRole(String username, String roleName) {
        if(validateUsername(username) && validateRoleName(roleName)) {
            if (checkRoleWasContainedByUser(username,roleName)) {
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
        }else{
            throw new UsernameNotFoundException(username);
        }
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
        if(validateUsername(username)){
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
        if(validateUsername(username)){
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
        if(validateUsername(username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<RoleApplication> namesRole = (List<RoleApplication>) entityManager.createQuery(
                    "SELECT r FROM RoleApplication r JOIN r.userRole ur JOIN ur.userApplication u WHERE u.username = :username"
            ).setParameter("username",username).getResultList();

            List<SimpleGrantedAuthority> namesRoleToString = namesRole.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList());
            return namesRoleToString;
        }
        return null;
    }

    @Override
    public List<Course> addCourse(String username, String courseName, String header) {
        if(checkSelfOwner(header,username)){
            if(courseRepository.existsCourseByName(courseName)){
                Course course = courseRepository.findCourseByName(courseName);
                UserApplication userApplication = userApplicationRepository.findUserApplicationsByUsername(username);
                userCourseService.addNewCourse(userApplication,course);
                return getCourses(username,header);
            }else {
                throw new CanNotFoundCourseException(courseName);
            }
        }else{
            throw new AccessPermissionDeniedException();
        }
    }

    @Override
    public UserCourse editCourse(String username, String courseName,UserCourse userCourse, String header) {
        if(checkSelfOwner(header,username)){
            UserCourse oldUserCourse = userCourseService.getUserCourse(username,courseName);
            userCourse.setId(oldUserCourse.getId());
            return userCourseRepository.save(userCourse);
        }else{
            throw new AccessPermissionDeniedException();
        }
    }

    @Override
    public List<Course> getCourses(String username, String header) {
        if(checkSelfOwner(header,username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<Course> courses = (List<Course>) entityManager.createQuery("SELECT c from Course c" +
                    " JOIN c.userCourses uc JOIN uc.userApplication u WHERE u.username=:username")
                    .setParameter("username",username).getResultList();
            return courses;
        }else {
            throw new AccessPermissionDeniedException();
        }
    }

    @Override
    public Course getCourseByName(String username, String courseName, String header) {
        if(checkSelfOwner(header,username)){
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Course course = (Course)entityManager.createQuery("SELECT c FROM Course c" +
                    " JOIN c.userCourses uc JOIN uc.userApplication u " +
                    "WHERE u.username=:username AND c.name=:courseName").setParameter("username",username)
                    .setParameter("courseName",courseName).getSingleResult();
            return course;
        }else {
            throw new AccessPermissionDeniedException();
        }
    }

    @Override
    public void deleteCourse(String username, String courseName, String header) {

    }

    @Override
    public UserCourse updateAmountCompleted(String username, String courseName, Long amountCompleted, String header) {
        System.out.println("xxx0");
        if(checkSelfOwner(header,username)){
            System.out.println("xxx1");
            Course course = getCourseByName(username,courseName,header);
            if(course != null){
                System.out.println("xxx3");
                UserCourse userCourse = userCourseService.getUserCourse(username,courseName);
                if(course.getDuration().longValue() == amountCompleted.longValue()){
                    userCourse.setCompleted(true);
                }
                userCourse.setAmountCompleted(amountCompleted);
                return userCourseRepository.save(userCourse);
            }else{
                System.out.println("xxx4");
                throw new CanNotFoundUserCourseException();
            }
        }else {
            System.out.println("xxx2");
            throw new AccessPermissionDeniedException();
        }
    }


}
