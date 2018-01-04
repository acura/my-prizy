package com.sarathi.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import jline.internal.Log;

import com.sarathi.strategy.PricingStategy;

public class ReflectionHelper {

    private ReflectionHelper() {
	}

	public static synchronized List<Class<?>> findClassesImpmenenting() {
    	final Class<?> interfaceClass = PricingStategy.class;
    	final Package fromPackage = PricingStategy.class.getPackage();
    	
        if (fromPackage == null) {
            return Collections.emptyList();
        }

        final List<Class<?>> rVal = new ArrayList<>();
        try {
            final Class<?>[] targets = getAllClassesFromPackage(fromPackage.getName());
            if (targets != null) {
                for (Class<?> aTarget : targets) {
                	if(aTarget == null || aTarget.equals(interfaceClass) || !interfaceClass.isAssignableFrom(aTarget)) {
                		continue;
                	} else {
                		rVal.add(aTarget);
                	}
                }
            }
        } catch (ClassNotFoundException | IOException e) {
          Log.error(e.getMessage());
        }

        return rVal;
    }

    public static Class<?>[] getAllClassesFromPackage(final String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
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
        List<Class<?>> classes = new ArrayList<>();
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