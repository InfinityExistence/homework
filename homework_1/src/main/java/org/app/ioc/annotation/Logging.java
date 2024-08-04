package org.app.ioc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Помечает метод, как необходимый для логгирования
 * Логи сохраняются в LogRepository
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
    String MethodDescription() default "";
}
