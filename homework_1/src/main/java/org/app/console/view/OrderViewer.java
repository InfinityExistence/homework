package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.EntityPrinter;
import org.app.console.view.output.element.Message;
import org.app.controller.CarController;
import org.app.controller.OrderController;
import org.app.controller.UserDataController;
import org.app.entity.Car;
import org.app.entity.Order;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;
import org.app.ioc.annotation.AutoInjected;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderViewer {
    private final String orderUserMenu = """
            0. Вернуться в меню.
            1. Создание заказа на покупку автомобиля.
            2. Просмотр моих заказов.
            3. Удаление заказа.""";
    private final String orderMangerMenu = orderUserMenu + """
            
            4. Просмотр заказов всех пользователей.""";
    private final String orderAdminMenu = orderMangerMenu + """
            
            5. Оформить заказ пользователю.
            6. Изменить заказ пользователю.
            7. Удалить заказ пользователю.""";
    @AutoInjected
    Message message;
    @AutoInjected
    Reader reader;
    @AutoInjected
    OrderController orderController;
    @AutoInjected
    CarController carController;
    @AutoInjected
    UserDataController userDataController;
    @AutoInjected
    EntityPrinter entityPrinter;


    public void viewOrderActions(UserCredentials userCredentials) {
        String menu = selectMenu(userCredentials.getRoles());
        message.printMessage(menu);
        Integer currentAction = reader.readNonNullInteger("действие");
        chooseAction(currentAction, userCredentials);
    }
    private String selectMenu(List<String> roles) {
        if (roles.contains("ADMIN"))
            return orderAdminMenu;
        else if (roles.contains("MANAGER"))
            return orderMangerMenu;
        else return orderUserMenu;
    }

    private void chooseAction(Integer currentAction, UserCredentials credentials) {
        switch (currentAction) {
            case 1 -> createMyOrder(credentials);
            case 2 -> viewMyOrders(credentials);
            case 3 -> deleteMyOrder(credentials);
            case 4 -> viewAllOrders(credentials);
            case 5 -> createOrderToUser(credentials);
            case 6 -> editOrderToUser(credentials);
            case 7 -> deleteOrderToUser(credentials);
        }
    }

    private void deleteOrderToUser(UserCredentials credentials) {
        var orderId = reader.readUUID("UUID заказа");
        Order order = orderController.getOrderById(orderId, credentials);
        orderController.deleteOrder(order, credentials);
    }

    private void editOrderToUser(UserCredentials credentials) {
        var orderId = reader.readUUID("UUID заказа");
        Order order = orderController.getOrderById(orderId, credentials);
        String newOrderState = reader.readProperty("новый статус заказа");
        order.setStatus(newOrderState);
        orderController.editOrder(order, credentials);
    }

    private void viewAllOrders(UserCredentials credentials) {
        List<Order> allOrders = orderController.getAllOrders(credentials);
        entityPrinter.printAllOrdersWithHeader(allOrders);
        reader.waitToEnterInput();
    }

    private void createOrderToUser(UserCredentials credentials) {
        var carId = reader.readUUID("UUID автомобиля");
        Car car = carController.getCarByUUID(carId);
        var userId = reader.readUUID("UUID клиента");
        UserData clientData = userDataController.findUser(userId, credentials);
        Order order = orderController.makeOrder(car, clientData, credentials);
        message.printMessage("Создан заказ, UUID " + order.getId());

    }

    private void deleteMyOrder(UserCredentials credentials) {
        var orderId = reader.readUUID("UUID заказа");
        Order order = orderController.getMyOrders(credentials)
                .stream().filter(myOrder ->
                        myOrder.getId().equals(orderId)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("У вас нет такого заказа"));
        orderController.deleteMyOrder(order,credentials);
    }

    private void viewMyOrders(UserCredentials credentials) {
        List<Order> myOrders = orderController.getMyOrders(credentials);
        entityPrinter.printAllOrdersWithHeader(myOrders);
        reader.waitToEnterInput();
    }

    private void createMyOrder(UserCredentials credentials) {
        var id = reader.readUUID("UUID автомобиля");
        Car car = carController.getCarByUUID(id);
        Order order = orderController.makeMyOrder(car, credentials);
        message.printMessage("Создан заказ, UUID " + order.getId());
    }

}
