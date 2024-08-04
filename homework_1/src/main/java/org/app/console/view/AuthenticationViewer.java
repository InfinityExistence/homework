package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.element.Message;
import org.app.controller.AuthenticationControllerImpl;
import org.app.entity.UserCredentials;
import org.app.exception.AuthenticationException;
import org.app.exception.ForbiddenException;
import org.app.exception.WrongActionException;
import org.app.ioc.annotation.AutoInjected;

public class AuthenticationViewer {
    private final String helloMessage = """
                Зарегистрируйтесь или войдите в аккаунт!
                        
                1. Зарегистрироваться.
                2. Войти в аккаунт.
                """;
    private final String successRegister = """
                Вы успешно зарегистрировались, давайте теперь введем данные в вашем профиле.""";
    @AutoInjected
    AuthenticationControllerImpl authenticationController;
    @AutoInjected
    Message message;
    @AutoInjected
    private Reader reader;
    @AutoInjected
    private UserDataViewer userDataViewer;
    public UserCredentials init() {
        message.printMessage(helloMessage);
        Integer currentAction = reader.readNonNullInteger("действие");
        return chooseAction(currentAction);
    }

    private UserCredentials chooseAction(Integer input) {
        try {
            switch (input) {
                case 1 -> {
                    return getRegister();
                }
                case 2 -> {
                    return getAuthenticate();
                }
                default -> {
                    throw new WrongActionException("Выбрано не существующее действие");
                }
            }
        } catch (AuthenticationException e) {
            message.printMessage(e.getMessage());
            return chooseAction(input);
        }
    }

    private UserCredentials getAuthenticate() throws AuthenticationException {
        try {
            UserCredentials userCredentialsInput = reader.readUserCredentials();

            return authenticationController.authenticate(userCredentialsInput);
        } catch (ForbiddenException e) {
            message.printMessage(e.getMessage());
            return getAuthenticate();
        }
    }

    private UserCredentials getRegister() throws AuthenticationException {
        try {
            UserCredentials userCredentials = reader.readUserCredentials();
            UserCredentials registered = authenticationController.register(userCredentials);
            message.printMessage(successRegister);

            userDataViewer.addUserData(registered);
            return registered;
        } catch (ForbiddenException e) {
            message.printMessage(e.getMessage());
            return getRegister();
        }
    }
}
