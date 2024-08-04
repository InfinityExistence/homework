package org.app.service;

import org.app.entity.UserCredentials;
import org.app.exception.AuthenticationException;

public interface AuthenticationService {
    UserCredentials authenticate(UserCredentials userCredentials) throws AuthenticationException;
    UserCredentials register(UserCredentials userCredentials) throws AuthenticationException;
}
