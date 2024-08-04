package org.app.ioc;


import org.app.ioc.context.ApplicationContext;
import org.app.ioc.di.factory.ObjectFactory;
import org.app.ioc.di.scanner.ClassScanner;

public class Application {
    public static ApplicationContext run(String packageToScan) {
        var scanner = new ClassScanner(packageToScan);
        var factory = new ObjectFactory();
        return new ApplicationContext(scanner, factory);
    }
}

