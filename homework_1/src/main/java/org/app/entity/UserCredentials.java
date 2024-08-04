package org.app.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserCredentials {
    private UserData data;
    private String login;
    private String password;
    private List<String> roles;
}
