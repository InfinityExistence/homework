package org.app.ioc.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object o, Class implementation);

    default List<Method> getIfcMethods(Class implementation) {
        List<Method> methods = new ArrayList<>();
        Class[] interfaces = implementation.getInterfaces();
        for (var ifc : interfaces)
            methods.addAll(List.of(ifc.getMethods()));
        return methods;
    }

}
