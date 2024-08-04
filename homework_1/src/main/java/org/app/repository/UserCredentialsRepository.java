package org.app.repository;

import org.app.entity.UserCredentials;

public interface UserCredentialsRepository {
    UserCredentials save(UserCredentials userData);
    UserCredentials findByLogin(String login);
    void deleteByLogin(String login);
    boolean existsByLogin(String login);
}
