package org.app.service;

import org.app.entity.Log;
import org.app.ioc.annotation.AutoInjected;
import org.app.repository.LogRepository;

import java.util.List;

public class LogServiceImpl implements LogService {
    @AutoInjected
    LogRepository logRepository;
    @Override
    public void saveLog(Log log) {
        logRepository.saveLog(log);
    }

    @Override
    public List<Log> findAllLogs() {
        return logRepository.findAllLogs();
    }
}
