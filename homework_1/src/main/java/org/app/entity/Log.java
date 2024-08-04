package org.app.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class Log {
    UUID user;
    String userName;
    String action;
    LocalDateTime time;
}
