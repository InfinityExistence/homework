package org.app.repository;

import org.app.entity.UserData;

import java.util.List;
import java.util.UUID;

public interface UserDataRepository {
    UserData save(UserData userData);
    UserData findById(UUID id);
    List<UserData> findAll();
    void delete(UserData userData);
}
