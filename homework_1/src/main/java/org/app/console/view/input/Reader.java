package org.app.console.view.input;

import org.app.entity.Car;
import org.app.entity.UserCredentials;
import org.app.entity.UserData;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class Reader {
    private final Scanner in = new Scanner(System.in);
    public UserCredentials readUserCredentials() {
        return UserCredentials.builder()
                .login(readProperty("логин"))
                .password(readProperty("пароль"))
                .build();
    }

    public Car readCar() {
        return Car.builder()
                .brand(readProperty("марку"))
                .model(readProperty("модель"))
                .year(readInteger("год выпуска"))
                .price(readDouble("цену"))
                .condition(readProperty("состояние"))
                .build();
    }
    public UUID readUUID(String property) {
        return UUID.fromString(readProperty(property));

    }


    public UserData readUser(UserCredentials userCredentials) {
        UserData userData = UserData.builder()
                .name(readProperty("имя"))
                .email(readProperty("адрес электронной почты"))
                .phone(readProperty("номер телефона"))
                .orders(new ArrayList<>())
                .userCredentials(userCredentials)
                .build();
        userCredentials.setData(userData);
        return userData;
    }

    public Integer readNonNullInteger(String property) {
        Integer input = null;
        while (input == null)
            input = readInteger(property);
        return input;
    }

    public Integer readInteger(String property) {
        return readNumberWithParser(property, Integer::parseInt);
    }
    public Double readDouble(String property) {
        return readNumberWithParser(property, Double::parseDouble);
    }
    private <T extends Number> T readNumberWithParser(String property, Function<String , T> parser) {
        try {
            String input = readProperty(property);
            if (input != null)
                return parser.apply(input);
        }
        catch (NumberFormatException e) {
            System.out.println("Не правильно введено число");
            return readNumberWithParser(property, parser);
        }
        return null;
    }
    public String readProperty(String property) {
        System.out.printf("Введите %s: ", property);
        String input = in.nextLine();
        if (!input.isBlank())
            return input;
        return null;
    }

    public void waitToEnterInput() {
        readProperty("Enter, чтобы выйти");
    }
}
