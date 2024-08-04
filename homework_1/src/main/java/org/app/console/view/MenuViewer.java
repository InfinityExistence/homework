package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.element.Message;
import org.app.entity.UserCredentials;
import org.app.exception.ForbiddenException;
import org.app.ioc.annotation.AutoInjected;

import java.util.NoSuchElementException;

public class MenuViewer {
    private final String userMenu = """
            Добро пожаловать в меню автосалона!
                            
            Панель действий:
            1. Просмотр автомобилей.
            2. Просмотр заказов.
            3. Просмотр профиля.
                        
            0. Выйти из аккаунта.""";
    private final String adminMenu = """
            Добро пожаловать в меню автосалона!
                            
            Панель действий:
            1. Просмотр автомобилей.
            2. Просмотр заказов.
            3. Просмотр профиля.
            4. Просмотр логов.
                        
            0. Выйти из аккаунта.""";
    @AutoInjected
    private OrderViewer orderViewer;
    @AutoInjected
    private CarViewer carViewer;
    @AutoInjected
    private UserDataViewer userDataViewer;
    @AutoInjected
    private LogViewer logViewer;
    @AutoInjected
    private Reader reader;
    @AutoInjected
    private Message message;


    public void viewMenu(UserCredentials userCredentials) {
        while (true) {
            try {
                message.printMessage(selectMenu(userCredentials));
                Integer currentAction = reader.readNonNullInteger("действие");
                if (currentAction == 0)
                    return;
                chooseAction(currentAction, userCredentials);
            } catch (ForbiddenException | NoSuchElementException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                System.out.println("Произошла внутренняя ошибка");
            }

        }
    }

    private String selectMenu(UserCredentials userCredentials) {
        if (userCredentials.getRoles().contains("ADMIN"))
            return adminMenu;
        return userMenu;
    }

    private void chooseAction(Integer currentAction, UserCredentials userCredentials) {
        switch (currentAction) {
            case 1 -> carViewer.viewCarActions(userCredentials);
            case 2 -> orderViewer.viewOrderActions(userCredentials);
            case 3 -> userDataViewer.viewUserActions(userCredentials);
            case 4 -> logViewer.viewALlLogs(userCredentials);
            default -> message.printMessage("Выбранное действие не существует");
        }
    }
}
