package com.locngo.zamo.io.internshipdemo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class APIException{

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yy hh:mm:ss")
    private LocalDateTime timeError;
    private HttpStatus httpStatus;
    private String message;

    public APIException() {
        this.timeError = LocalDateTime.now();
    }

    public APIException(HttpStatus httpStatus, String message) {
        this.timeError = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeError() {
        return timeError;
    }

    public void setTimeError(LocalDateTime timeError) {
        this.timeError = timeError;
    }
}
