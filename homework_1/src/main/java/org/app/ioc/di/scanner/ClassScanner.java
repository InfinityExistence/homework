package org.app.ioc.di.scanner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassScanner {
    private final String rootPackageName;
    private final Set<Class<?>> classes = new HashSet<>(30);
    private final Set<String> subPackages = new HashSet<>(10);

    public ClassScanner(String rootPackageName) {
        this.rootPackageName = rootPackageName;
        updateSubPackagesAndLoadClasses();
    }

    void updateSubPackagesAndLoadClasses() {
        updateSubPackages();
        subPackages.forEach(this::loadAllClasses);
    }
    private void updateSubPackages() {
        Arrays.stream(Package.getPackages())
                .map(Package::getName)
                .filter(pkg -> pkg.startsWith(rootPackageName))
                .forEach(subPackages::add);
    }
    private void loadAllClasses(String packageName) {
        try (InputStream stream = ClassLoader
                .getSystemResourceAsStream(replacePackageToPath(packageName));

             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(addNameToPackage(packageName, line)))
                    .filter(type -> type != null && !type.isInterface())
                    .forEach(classes::add);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Не удалось загрузить классы");
        }
    }
    private String replacePackageToPath(String packageName) {
        return packageName.replaceAll("[.]", "/");
    }
    private String addNameToPackage(String packageName, String line) {
        return packageName + "." + line.substring(0, line.lastIndexOf('.'));
    }

    private Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ignored) {
            System.out.println(name + " не найден!");
        }
        return null;
    }

    public <T> Class<? extends T> getImplClass(Class<T> type) {
        Set<Class<?>> implementations = getImplClasses(type);

        if (implementations.isEmpty())
            throw new RuntimeException("У интерфейса " + type.getName() + " не найдена реализация");
        if (implementations.size() > 1)
            throw new RuntimeException("У интерфейса " + type.getName() + "более одной реализации");

        return (Class<? extends T>) implementations.iterator().next();
    }

    public <T> Set<Class<?>> getImplClasses(Class<T> type) {
        updateSubPackagesAndLoadClasses();

        return classes.stream()
                .filter(clazz -> isClassImplementsInterface(type, clazz))
                .collect(Collectors.toSet());
    }

    private <T> boolean isClassImplementsInterface(Class<T> ifc, Class clazz) {
        return Arrays.asList(clazz.getInterfaces()).contains(ifc);
    }


}
