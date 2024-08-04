package org.app.controller;

import org.app.entity.UserCredentials;
import org.app.exception.AuthenticationException;
import org.app.ioc.annotation.AutoInjected;
import org.app.service.AuthenticationService;
import org.app.service.UserDataService;

public class AuthenticationControllerImpl implements AuthenticationController {


    @AutoInjected
    private AuthenticationService authenticationService;
    @AutoInjected
    private UserDataService userDataService;
    public UserCredentials register(UserCredentials userCredentialsInput) throws AuthenticationException {
        return authenticationService.register(userCredentialsInput);
    }

    public UserCredentials authenticate(UserCredentials userCredentialsInput) throws AuthenticationException {

        return authenticationService.authenticate(userCredentialsInput);
    }

}
