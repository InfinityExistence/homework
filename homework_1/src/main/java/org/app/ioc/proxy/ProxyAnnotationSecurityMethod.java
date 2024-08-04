package org.app.ioc.proxy;

import org.app.entity.UserCredentials;
import org.app.exception.ForbiddenException;
import org.app.ioc.annotation.HasAnyRole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Обработка аннотации HasAnyRole
 */
public class ProxyAnnotationSecurityMethod implements ProxyConfigurator {
    @Override
    public Object replaceWithProxyIfNeeded(Object o, Class implementation) {
        List<Method> methods = getIfcMethods(implementation);
        for (Method method : methods) {
            method.setAccessible(true);
            if (isMethodAnnotatedHasRole(method)) {
                return Proxy.newProxyInstance(implementation.getClassLoader(), implementation.getInterfaces(),
                        (proxy, method1, args) -> getInvocationHandlerLogic(method1, args, o));
            }
        }
        return o;
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t) throws IllegalAccessException, InvocationTargetException {
        if (isMethodAnnotatedHasRole(method)) {
            boolean accepted = false;
            for (var o : args)
                if (o instanceof UserCredentials credentials)
                    accepted = checkUserHaveAnyRole(credentials, method);


            if (!accepted)
                throw new ForbiddenException("У вас недостаточно прав для данного действия");

        }
        return method.invoke(t, args);
    }

    private boolean checkUserHaveAnyRole(UserCredentials credentials, Method method) {
        var roles = method.getAnnotation(HasAnyRole.class).roles();
        var userRoles = credentials.getRoles();
        for (var role : roles)
            if (userRoles.contains(role))
                    return true;

        return false;
    }

    private boolean isMethodAnnotatedHasRole(Method method) {
        return method.isAnnotationPresent(HasAnyRole.class);
    }

}
