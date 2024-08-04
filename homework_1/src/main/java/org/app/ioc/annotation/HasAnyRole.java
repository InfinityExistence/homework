package org.app.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Проверяет на наличие в передаваемой в метод UserCredentials хотябы одной из ролей
 * Если необходимых ролей не найдено, выбрасывается исключение ForbiddenException
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface HasAnyRole {
    String[] roles() default {};


}
