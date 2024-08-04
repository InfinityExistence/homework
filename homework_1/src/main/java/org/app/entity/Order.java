package org.app.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Order {
    private UUID id;
    private UserData client;
    private Car car;
    private String status;
}
