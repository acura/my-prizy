package com.sarathi.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.sarathi.strategy.PricingStategy;

public class ReflectionHelper {

    public synchronized static List<Class<?>> findClassesImpmenenting() {
    	final Class<?> interfaceClass = PricingStategy.class;
    	final Package fromPackage = PricingStategy.class.getPackage();
    	
        if (fromPackage == null) {
            return null;
        }

        final List<Class<?>> rVal = new ArrayList<Class<?>>();
        try {
            final Class<?>[] targets = getAllClassesFromPackage(fromPackage.getName());
            if (targets != null) {
                for (Class<?> aTarget : targets) {
                    if (aTarget == null) {
                        continue;
                    }
                    else if (aTarget.equals(interfaceClass)) {
                        continue;
                    }
                    else if (!interfaceClass.isAssignableFrom(aTarget)) {
                        continue;
                    }
                    else {
                        rVal.add(aTarget);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return rVal;
    }

    public static Class<?>[] getAllClassesFromPackage(final String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}