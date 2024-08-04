package org.app.controller;

import org.app.entity.Log;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.HasAnyRole;

import java.util.List;

public interface LogController {
    @HasAnyRole(roles = {"ADMIN"})
    List<Log> getAllLogs(UserCredentials userCredentials);
}
