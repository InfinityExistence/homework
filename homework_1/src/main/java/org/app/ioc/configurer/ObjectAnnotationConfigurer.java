package org.app.ioc.configurer;

import lombok.SneakyThrows;
import org.app.ioc.annotation.AutoInjected;
import org.app.ioc.context.ApplicationContext;

/**
 * Обработка аннотации AutoInjected
 */
public class ObjectAnnotationConfigurer implements ObjectConfigurer {
    @Override
    @SneakyThrows
    public void configure(Object instance, ApplicationContext context) {
        for (var field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjected.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(instance, object);
            }
        }
    }
}
