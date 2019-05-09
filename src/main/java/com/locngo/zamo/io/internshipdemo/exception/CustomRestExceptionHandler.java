package com.locngo.zamo.io.internshipdemo.exception;

import com.locngo.zamo.io.internshipdemo.exception.course.*;
import com.locngo.zamo.io.internshipdemo.exception.jwt.InvalidTokenException;
import com.locngo.zamo.io.internshipdemo.exception.roleapplication.*;
import com.locngo.zamo.io.internshipdemo.exception.userapplication.*;
import com.locngo.zamo.io.internshipdemo.exception.usercourse.CanNotFoundUserCourseException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Bean
    public ErrorAttributes errorAttributes(){
       return new DefaultErrorAttributes(){
           @Override
           public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
               return super.getErrorAttributes(webRequest, includeStackTrace);
           }
       };
    }

    @ExceptionHandler(TestException.class)
    public ResponseEntity<APIException> handleTestException(Exception exception){
        APIException apiException = new APIException();
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiException.setMessage(exception.getMessage());
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

    @ExceptionHandler(UsernameCanNotBeEmptyException.class)
    public ResponseEntity<APIException> handleUsernameCanNotBeEmpty(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(PasswordCanNotBeEmptyException.class)
    public ResponseEntity<APIException> handlePasswordCanNotBeEmpty(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(UsernameWasExistException.class)
    public ResponseEntity<APIException> handleUsernameWasExistException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(EmailCanNotBeEmptyException.class)
    public ResponseEntity<APIException> handleEmailCanNotBeEmptyException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(NameCanNotBeEmptyException.class)
    public ResponseEntity<APIException> handleNameCanNotBeEmpty(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(NotEnoughUserFieldException.class)
    public ResponseEntity<APIException> handleNotEnoughFileUserException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<APIException> handleUserNotExistException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(RoleNotExistException.class)
    public ResponseEntity<APIException> handleRoleNotExistException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CanNotFoundUserByUsernameException.class)
    public ResponseEntity<APIException> handleCanNotFoundUserByUsernameException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CanNotFoundRoleByNameException.class)
    public ResponseEntity<APIException> handleCanNotFoundRoleByNameException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(RoleWasExistedInUserException.class)
    public ResponseEntity<APIException> handleRoleWasExistedInUserException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }


    @ExceptionHandler(RoleNameWasExistException.class)
    public ResponseEntity<APIException> handleRoleNameWasExistException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(RoleIsNullException.class)
    public ResponseEntity<APIException> handleRoleIsNullException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<APIException> handleInvalidTokenException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CourseFieldNotEnoughException.class)
    public ResponseEntity<APIException> handleCourseFieldNotEnoughException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CourseNameNotBeEmptyException.class)
    public ResponseEntity<APIException> handleCourseNameNotBeEmptyException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<APIException> handleInvalidInputException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CanNotFoundCourseException.class)
    public ResponseEntity<APIException> handleCanNotFoundCourseException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CanNotFoundCourseByIdException.class)
    public ResponseEntity<APIException> handleCanNotFoundCourseByIdException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CourseNameExistedException.class)
    public ResponseEntity<APIException> handleCourseNameExistedException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(AccessPermissionDeniedException.class)
    public ResponseEntity<APIException> handleAccessPermissionDeniedException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }

    @ExceptionHandler(CanNotFoundUserCourseException.class)
    public ResponseEntity<APIException> handleCanNotFoundUserCourseException(Exception exception){
        APIException apiException = new APIException();
        apiException.setMessage(exception.getMessage());
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException,apiException.getHttpStatus());
    }
}
