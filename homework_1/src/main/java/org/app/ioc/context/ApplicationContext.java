package org.app.ioc.context;

import lombok.SneakyThrows;
import org.app.ioc.configurer.ObjectConfigurer;
import org.app.ioc.di.factory.ObjectFactory;
import org.app.ioc.di.scanner.ClassScanner;
import org.app.ioc.proxy.ProxyConfigurator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Разрешает зависимости в полях объектов
 * Настраивает объекты при создании
 * Кеширует объекты как singleton
 */
public class ApplicationContext {
    private final ClassScanner scanner;
    private final ObjectFactory factory;
    private final Map<Class, Object> cache = new HashMap<>();
    private final Set<? extends ObjectConfigurer> objectConfigurers;
    private final Set<? extends ProxyConfigurator> proxyConfigurers;
    @SneakyThrows
    public ApplicationContext(ClassScanner scanner, ObjectFactory factory) {
        this.scanner = scanner;
        this.factory = factory;
        objectConfigurers = (Set<? extends ObjectConfigurer>) scanner.getImplClasses(ObjectConfigurer.class).stream()
                .map(factory::createObject).collect(Collectors.toSet());
        proxyConfigurers = (Set<? extends ProxyConfigurator>) scanner.getImplClasses(ProxyConfigurator.class).stream()
                .map(factory::createObject).collect(Collectors.toSet());

    }

    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = scanner.getImplClass(type);
        }
        T t = factory.createObject(implClass);
        configureObject(t);

        t = wrapWithProxyIfNeeded(t, implClass);

        cache.put(type, t);


        return t;
    }

    private <T> T wrapWithProxyIfNeeded(T t, Class<? extends T> implClass) {
        for (var proxyConf : proxyConfigurers)
            t = (T) proxyConf.replaceWithProxyIfNeeded(t, implClass);
        return t;
    }

    private <T> void configureObject(T t) {
        objectConfigurers.forEach(configurer ->
                configurer.configure(t, this));
    }
}
