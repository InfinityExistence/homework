package org.app.service;

import org.app.entity.Log;

import java.util.List;

public interface LogService {
    void saveLog(Log log);

    List<Log> findAllLogs();
}
