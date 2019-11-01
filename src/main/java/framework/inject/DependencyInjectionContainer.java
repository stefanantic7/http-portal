package framework.inject;

import framework.inject.specifications.Autowire;
import framework.route.Route;
import framework.services.RouteLoader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DependencyInjectionContainer {

    //Singleton on request level.
    private HashMap<Class<?>, Class<?>> singletonContainer;
    private HashMap<Class<?>, Object> singletonInstances;

    private HashMap<Class<?>, Class<?>> container;

    public DependencyInjectionContainer() {
        this.singletonContainer = new HashMap<Class<?>, Class<?>>();
        this.singletonInstances = new HashMap<Class<?>, Object>();

        this.container = new HashMap<Class<?>, Class<?>>();
    }

    public void singleton(Class<?> abstraction, Class<?> concrete) {
        this.singletonContainer.put(abstraction, concrete);
    }

    public void bind(Class<?> abstraction, Class<?> concrete) {
        this.container.put(abstraction, concrete);
    }

    private Object getSingletonInstance(Class clazz) {
        if (this.singletonInstances.get(clazz) == null) {
            synchronized (DependencyInjectionContainer.class) {
                if (this.singletonInstances.get(clazz) == null) {
                    this.singletonInstances.put(clazz, this.newInstanceOfClass(clazz));
                }
            }
        }

        return this.singletonInstances.get(clazz);
    }

    private Object newInstanceOfClass(Class clazz) {
        Object o = null;
        try {
            o = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(Autowire.class) != null) {
                    Object attribute = this.make(field.getType());
                    field.setAccessible(true);
                    field.set(o, attribute);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Resolve instance for given abstraction.
     *
     * @param abstraction: abstraction representation of instance
     * @return new object for given abstraction
     */
    public Object make(Class<?> abstraction) {
        Object o = null;

        //First check for singleton
        Class clazz = singletonContainer.get(abstraction);

        if(clazz != null) {
            o = this.getSingletonInstance(clazz);
        }
        else {
            o = this.newInstanceOfClass(container.get(abstraction));
        }

        //TODO Create Exception if class is not instantiatable

        return o;
    }
}
