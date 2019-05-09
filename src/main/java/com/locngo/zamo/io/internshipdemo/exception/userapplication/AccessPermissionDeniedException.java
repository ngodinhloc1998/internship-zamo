package com.locngo.zamo.io.internshipdemo.exception.userapplication;

public class AccessPermissionDeniedException extends RuntimeException {
    public AccessPermissionDeniedException() {
        super("You do not have permission for this service");
    }
}
