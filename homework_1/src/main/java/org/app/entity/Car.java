package org.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    UUID id;
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    private String condition;
    private Order order;
    public boolean isOrdered() {
        return order != null;
    }
}
