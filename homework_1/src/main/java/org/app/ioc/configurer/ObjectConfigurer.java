package org.app.ioc.configurer;

import org.app.ioc.context.ApplicationContext;

public interface ObjectConfigurer {
    void configure(Object object, ApplicationContext context);
}
