package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.EntityPrinter;
import org.app.console.view.output.element.Message;
import org.app.controller.CarController;
import org.app.entity.Car;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.AutoInjected;

import java.util.List;
import java.util.UUID;

public class CarViewer {
    private final String carUserMenu = """
            0. Вернуться в меню
            1. Просмотр всех автомобилей.
            2. Поиск автомобилей.""";
    private final String carManagerMenu = carUserMenu + """
            
            3. Добавление нового автомобиля.
            4. Редактирование информации об автомобиле.
            5. Удаление автомобиля из списка.""";
    @AutoInjected
    Message message;
    @AutoInjected
    private Reader reader;
    @AutoInjected
    CarController carController;
    @AutoInjected
    private EntityPrinter entityPrinter;

    public void viewCarActions(UserCredentials credentials) {
        String menu = selectMenu(credentials.getRoles());
        message.printMessage(menu);
        Integer currentAction = reader.readNonNullInteger("действие");
        chooseAction(currentAction, credentials);

    }
    private void chooseAction(Integer currentAction, UserCredentials credentials) {
        switch (currentAction) {
               case 0 -> {
                return;
            }
               case 1 -> viewCars(credentials);
               case 2 -> searchCars(credentials);
               case 3 -> addCar(credentials);
               case 4 -> editCar(credentials);
               case 5 -> deleteCar(credentials);
            
        }
    }

    private void deleteCar(UserCredentials credentials) {
        Car car = getCarById();
        carController.deleteCar(car, credentials);
    }

    private Car getCarById() {
        UUID carId = reader.readUUID("UUID автомобиля");
        return carController.getCarByUUID(carId);

    }

    private void editCar(UserCredentials credentials) {
        Car car = getCarById();
        entityPrinter.printCarWithHeader(car);
        var newCar = reader.readCar();
        newCar.setId(car.getId());
        carController.addCar(newCar,credentials);
    }

    private void addCar(UserCredentials credentials) {
        Car car = reader.readCar();
        carController.addCar(car, credentials);
    }

    private void searchCars(UserCredentials credentials) {
        message.printMessage("""
                Введите необходимые данные для поиска.
                Если критерий учитывать не нужно оставьте поле пустым.""");
        Car car = reader.readCar();
        List<Car> cars = carController.searchCars(car);
        entityPrinter.printAllCarsWithHeader(cars);
        reader.waitToEnterInput();

    }


    private void viewCars(UserCredentials credentials) {
        List<Car> cars = carController.viewCars();
        entityPrinter.printAllCarsWithHeader(cars);
        reader.waitToEnterInput();
    }

    private String selectMenu(List<String> roles) {
        if (roles.contains("ADMIN") || roles.contains("MANAGER"))
            return carManagerMenu;
        else return carUserMenu;
    }

}
