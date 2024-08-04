package org.app.controller;

import org.app.entity.Log;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.AutoInjected;
import org.app.service.LogService;

import java.util.List;

public class LogControllerImpl implements LogController {
    @AutoInjected
    LogService logService;
    @Override
    public List<Log> getAllLogs(UserCredentials userCredentials) {
        return logService.findAllLogs();
    }
}
