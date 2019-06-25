package com.dax.walker.models.exceptions;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
