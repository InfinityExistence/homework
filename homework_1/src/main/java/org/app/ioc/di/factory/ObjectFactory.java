package org.app.ioc.di.factory;

import lombok.SneakyThrows;


public class ObjectFactory {

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        T instance = type.getDeclaredConstructor().newInstance();

        return instance;
    }


}
