package org.app.controller;

import org.app.entity.UserCredentials;
import org.app.exception.AuthenticationException;

public interface AuthenticationController {
    UserCredentials register(UserCredentials userCredentialsInput) throws AuthenticationException;
    UserCredentials authenticate(UserCredentials userCredentialsInput) throws AuthenticationException;
}
