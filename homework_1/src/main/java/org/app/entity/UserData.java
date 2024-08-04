package org.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private UUID id;
    private UserCredentials userCredentials;
    private String name = "NoName";
    private String email;
    private String phone;
    private List<Order> orders;
}
