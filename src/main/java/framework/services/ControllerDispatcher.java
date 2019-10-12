package framework.services;

import framework.inject.DependencyInjectionContainer;
import framework.inject.DependencyInjectionEngine;
import framework.inject.specifications.Autowire;
import framework.response.Response;
import framework.route.Route;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ControllerDispatcher {

    private DependencyInjectionEngine dependencyInjectionEngine;

    public ControllerDispatcher(DependencyInjectionEngine dependencyInjectionEngine) {
        this.dependencyInjectionEngine = dependencyInjectionEngine;
    }

    public Response dispatch(Route route) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        String[] actionControllerMethod = route.getAction().split("@");
        String controllerClassName = actionControllerMethod[0];
        String methodName = actionControllerMethod[1];

        Object o = Class.forName("app.controllers."+controllerClassName).getDeclaredConstructor().newInstance();

        this.dependencyInjectionEngine.injectIn(o);

        return this.invokeMethod(o, methodName);

    }

    private Response invokeMethod(Object o, String methodName) throws InvocationTargetException, IllegalAccessException {
        Method method = null;
        Class<?>[] params = new Class<?>[0];

        for (Method m : o.getClass().getMethods()) {
            if (methodName.equals(m.getName())) {
                params = m.getParameterTypes();

                method = m;
                break;
            }
        }
        if (method == null) {
            throw new IllegalAccessException("Can not find the method: "+methodName);
        }

        return (Response) method.invoke(o, getParamsInstances(params));

    }

    private Object[] getParamsInstances(Class<?>[] params) {
        //TODO: Throw exception if param Class is not instantiable
        ArrayList<Object> paramsInstances = new ArrayList<Object>();

        for (Class<?> parameter: params) {
            paramsInstances.add(this.dependencyInjectionEngine.getDependencyInjectionContainer().make(parameter));
        }

        return paramsInstances.toArray();
    }

}
