package com.locngo.zamo.io.internshipdemo.exception;

import com.locngo.zamo.io.internshipdemo.exception.userapplication.*;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.ExpandVetoException;
import java.io.IOException;
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
}
