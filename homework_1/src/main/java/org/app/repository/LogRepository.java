package org.app.repository;

import org.app.entity.Log;

import java.util.List;

public interface LogRepository {
    void saveLog(Log log);
    List<Log> findAllLogs();
}
