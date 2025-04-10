package com.tpe.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {

        super(s); // parentin const ile set et

    }
}
