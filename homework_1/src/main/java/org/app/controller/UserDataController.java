package org.app.controller;

import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.HasAnyRole;
import org.app.ioc.annotation.Logging;

import java.util.List;
import java.util.UUID;

public interface UserDataController {
    @HasAnyRole(roles = {"ADMIN"})
    UserData findUser(UUID id, UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN", "MANGER"})
    List<UserData> findAllUsers(UserCredentials credentials);
    @HasAnyRole(roles = {"ADMIN"})
    @Logging(MethodDescription = "удаление пользователя")
    void deleteUser(UserData userData, UserCredentials credentials);

    UserData saveData(UserData userData);
}
