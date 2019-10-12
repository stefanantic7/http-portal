package framework.inject;

import framework.inject.specifications.Autowire;
import framework.inject.specifications.Bean;
import framework.inject.specifications.Component;
import framework.inject.specifications.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DependencyInjectionEngine {

    protected DependencyInjectionContainer dependencyInjectionContainer;

    public DependencyInjectionEngine(DependencyInjectionContainer dependencyInjectionContainer)
    {
        this.dependencyInjectionContainer = dependencyInjectionContainer;
    }

    public void loadClasses()
    {
        try {
            for (Class c : getClasses("app")) {

                String scope = "";
                if (c.getAnnotation(Bean.class) != null) {
                    scope = ((Bean)c.getAnnotation(Bean.class)).scope();
                }

                if (c.getAnnotation(Service.class) != null || scope.equals("singleton")) {
                    this.dependencyInjectionContainer.singleton(c, c);
                }
                else if (c.getAnnotation(Component.class) != null || scope.equals("prototype")) {
                    this.dependencyInjectionContainer.bind(c, c);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void injectIn(Object o) {
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Autowire.class) != null) {
                    Object instance = this.dependencyInjectionContainer.make(field.getType());

                    if(field.getAnnotation(Autowire.class).verbose()) {
                        System.out.println("Initialized "+field.getType()+" "+field.getName()+
                                " in "+o.getClass().getName()+" on "+ LocalDateTime.now() +
                                " with hash: "+instance.hashCode());
                    }

                    injectIn(instance);

                    field.setAccessible(true);
                    field.set(o, instance);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scans all classes accessible from the context class loader
     * which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public DependencyInjectionContainer getDependencyInjectionContainer() {
        return dependencyInjectionContainer;
    }
}
