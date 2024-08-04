package org.app.repository;

import org.app.entity.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogMemoryRepositoryTest {

    private LogMemoryRepository logRepository;
    private Log log1;
    private Log log2;

    @BeforeEach
    public void setUp() {
        logRepository = new LogMemoryRepository();

        log1 = Log.builder().action("Log message 1").build();

        log2 = Log.builder()
                .action("Log message 2")
                .build();
    }

    @Test
    public void testSaveLog() {
        logRepository.saveLog(log1);

        assertNotNull(log1.getTime());
        assertTrue(logRepository.findAllLogs().contains(log1));
    }

    @Test
    public void testSaveLogWithTime() {
        LocalDateTime time = LocalDateTime.now();
        log1.setTime(time);
        logRepository.saveLog(log1);

        assertEquals(time, log1.getTime());
        assertTrue(logRepository.findAllLogs().contains(log1));
    }

    @Test
    public void testFindAllLogs() {
        logRepository.saveLog(log1);
        logRepository.saveLog(log2);

        List<Log> logs = logRepository.findAllLogs();

        assertEquals(2, logs.size());
        assertTrue(logs.contains(log1));
        assertTrue(logs.contains(log2));
    }

    @Test
    public void testFindAllLogsEmpty() {
        List<Log> logs = logRepository.findAllLogs();

        assertTrue(logs.isEmpty());
    }
}
