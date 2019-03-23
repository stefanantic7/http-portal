package framework.services;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DependencyInjectionContainer {

    //Singleton on request level.
    private HashMap<Class<?>, Object> singletonContainer;

    private HashMap<Class<?>, Class<?>> container;

    public DependencyInjectionContainer() {
        this.singletonContainer = new HashMap<Class<?>, Object>();
        this.container = new HashMap<Class<?>, Class<?>>();
    }

    public void singleton(Class<?> abstraction, Object concrete) {
        this.singletonContainer.put(abstraction, concrete);
    }

    public void bind(Class<?> abstraction, Class<?> concrete) {
            this.container.put(abstraction, concrete);
    }

    /**
     * Resolve instance for given abstraction.
     *
     * @param abstraction: abstraction representation of instance
     * @return new object for given abstraction
     */
    public Object make(Class<?> abstraction) {
        //First check for singleton
        Object o = singletonContainer.get(abstraction);

        if(o == null) {
            Class<?> className = container.get(abstraction);
            try {
                o = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
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
        }

        //TODO Create Exception if class is not instantiatable

        return o;
    }
}
