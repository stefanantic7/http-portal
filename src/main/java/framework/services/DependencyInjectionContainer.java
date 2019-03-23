package framework.services;

import java.util.HashMap;

public class DependencyInjectionContainer {

    private HashMap<Class<?>, Object> container;

    public DependencyInjectionContainer() {
        this.container = new HashMap<Class<?>, Object>();
    }

    public void bind(Class<?> abstraction, Object concrete) {
        this.container.put(abstraction, concrete);
    }

    public Object make(Class<?> abstraction) {
        //TODO Create Exception if class is not instantiatable
        return container.get(abstraction);
    }
}
