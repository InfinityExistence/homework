package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.EntityPrinter;
import org.app.console.view.output.element.Message;
import org.app.controller.UserDataController;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;

import java.util.List;

public class UserDataViewer {
    private final String rolesMessage = """
            Выберите новые права пользователя:
            1. USER
            2. MANAGER
            3. ADMIN""";
    private final String userDataMenu = """
            0. Выйти в меню.
            1. Посмотреть мой профиль.""";
    private final String mangerDataMenu = userDataMenu + """
            
            2. Посмотреть все профили.""";
    private final String adminDataMenu = mangerDataMenu + """
            
            3. Изменить данные пользователя.
            4. Изменить роль пользователя.
            5. Удалить пользователя.""";
    @AutoInjected
    Reader reader;
    @AutoInjected
    UserDataController userDataController;
    @AutoInjected
    EntityPrinter entityPrinter;
    @AutoInjected
    Message message;


    public void addUserData(UserCredentials userCredentials) {
        var userData = reader.readUser(userCredentials);
        userDataController.saveData(userData);
    }

    public void viewUserActions(UserCredentials userCredentials) {
        String menu = selectMenu(userCredentials.getRoles());
        message.printMessage(menu);
        Integer currentAction = reader.readNonNullInteger("действие");
        chooseAction(currentAction, userCredentials);


    }
    private String selectMenu(List<String> roles) {
        if (roles.contains("ADMIN"))
            return adminDataMenu;
        else if (roles.contains("MANAGER"))
            return mangerDataMenu;
        else return userDataMenu;
    }
    private void chooseAction(Integer currentAction, UserCredentials credentials) {
        switch (currentAction) {
            case 1 -> viewMyProfile(credentials);
            case 2 -> viewAllUsers(credentials);
            case 3 -> editUserData(credentials);
            case 4 -> editUserRoles(credentials);
            case 5 -> deleteUser(credentials);
        }
    }


    public void viewAllUsers(UserCredentials userCredentials) {
        List<UserData> allUsers = userDataController.findAllUsers(userCredentials);
        entityPrinter.printAllUsersWithHeader(allUsers);
    }
    public void viewMyProfile(UserCredentials userCredentials) {
        entityPrinter.printUserWithHeader(userCredentials.getData());

    }
    public void deleteUser(UserCredentials userCredentials) {
        var userId = reader.readUUID("UUID пользователя");
        UserData user = userDataController.findUser(userId, userCredentials);
        userDataController.deleteUser(user, userCredentials);

    }
    public void editUserData(UserCredentials userCredentials) {
        var userId = reader.readUUID("UUID пользователя");

        UserData user = userDataController.findUser(userId, userCredentials);

        var userData = reader.readUser(user.getUserCredentials());
        userDataController.saveData(userData);
    }
    public void editUserRoles(UserCredentials userCredentials) {
        var userId = reader.readUUID("UUID пользователя");
        UserData user = userDataController.findUser(userId, userCredentials);
        var role = reader.readNonNullInteger(rolesMessage);
        user.getUserCredentials().setRoles(selectRole(role));
        userDataController.saveData(user);

    }

    private List<String> selectRole(Integer role) {
        return switch (role) {
            case 2 -> List.of("USER", "MANAGER");
            case 3 -> List.of("USER", "MANAGER", "ADMIN");
            default -> List.of("USER");
        };
    }
}
