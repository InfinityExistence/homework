package org.app.ioc.proxy;

import org.app.entity.Log;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.Logging;
import org.app.service.LogService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Обработка аннотации Logging
 */
public class ProxyAnnotationLoggingMetohd implements ProxyConfigurator{

    LogService logService;
    @Override
    public Object replaceWithProxyIfNeeded(Object o, Class implementation) {
        if (o instanceof LogService service)
            logService = service;

        List<Method> methods = getIfcMethods(implementation);
        for (Method method : methods) {
            method.setAccessible(true);
            if (isMethodAnnotatedForLogging(method)) {
                return Proxy.newProxyInstance(implementation.getClassLoader(), implementation.getInterfaces(),
                        (proxy, method1, args) -> getInvocationHandlerLogic(method1, args, o));
            }
        }
        return o;
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t) throws IllegalAccessException, InvocationTargetException {
        if (isMethodAnnotatedForLogging(method)) {
            for (var arg : args) {
                if (arg instanceof UserCredentials credentials) {
                    Log log = Log.builder()
                            .action(getAction(method))
                            .user(credentials.getData().getId())
                            .userName(credentials.getLogin()).build();

                    logService.saveLog(log);
                }
            }

        }
        return method.invoke(t, args);
    }

    private String getAction(Method method) {
        return method.getAnnotation(Logging.class).MethodDescription();
    }


    private boolean isMethodAnnotatedForLogging(Method method) {
        return method.isAnnotationPresent(Logging.class);
    }
}
