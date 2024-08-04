package org.app.service;

import org.app.entity.UserCredentials;
import org.app.exception.AuthenticationException;
import org.app.exception.ForbiddenException;
import org.app.ioc.annotation.AutoInjected;
import org.app.repository.UserCredentialsRepository;
import org.app.secure.password.PasswordEncoder;

import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService {
    @AutoInjected
    PasswordEncoder passwordEncoder;
    @AutoInjected
    UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials authenticate(UserCredentials userCredentials) throws AuthenticationException {
        credentialsIsNotBlank(userCredentials);
        UserCredentials userFromRepository =
                userCredentialsRepository.findByLogin(userCredentials.getLogin());
        if (isUserExistAndPasswordMatches(userCredentials, userFromRepository))
            return userFromRepository;
        throw new AuthenticationException("Неверный логин или пароль");

    }

    private boolean isUserExistAndPasswordMatches(UserCredentials userCredentials, UserCredentials userFromRepository) {
        return userFromRepository != null && passwordEncoder
                .matches(userCredentials.getPassword(), userFromRepository.getPassword());
    }

    @Override
    public UserCredentials register(UserCredentials userCredentials) throws AuthenticationException {
        if (userCredentialsRepository.existsByLogin(userCredentials.getLogin()))
            throw new AuthenticationException("Пользователь уже существует!");
        credentialsIsNotBlank(userCredentials);
        String encodedPassword = passwordEncoder.encode(userCredentials.getPassword());
        userCredentials.setPassword(encodedPassword);
        userCredentials.setRoles(List.of("USER"));
        return userCredentialsRepository.save(userCredentials);
    }

    private static void credentialsIsNotBlank(UserCredentials userCredentials) {
        if (userCredentials.getLogin() == null || userCredentials.getPassword() == null || userCredentials.getLogin().isBlank() || userCredentials.getPassword().isBlank())
            throw new ForbiddenException("Логин и пароль не должны быть пустыми");
    }
}
