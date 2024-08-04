package org.app.repository;

import org.app.entity.Log;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogMemoryRepository implements LogRepository {
    Set<Log> logs = new HashSet<>(100);


    @Override
    public void saveLog(Log log) {
        if (log.getTime() == null)
            log.setTime(LocalDateTime.now());
        logs.add(log);
    }

    @Override
    public List<Log> findAllLogs() {
        return logs.stream().toList();
    }
}
