package org.app.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Отметка для полей, которым необходимо разрешить зависимость от интерфейса/класса
 * ApplicationContext берет на себя ответственность за инъекцию зависимостей.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjected {
}
