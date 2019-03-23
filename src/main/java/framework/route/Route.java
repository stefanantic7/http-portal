package framework.route;

import framework.request.enums.Method;

public class Route {

    private Method method;
    private String location;
    private String action;

    public Route(Method method, String location, String action) {
        this.method = method;
        this.location = location;
        this.action = action;
    }

    public Method getMethod() {
        return method;
    }

    public String getLocation() {
        return location;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "<Route> method: "+this.getMethod()+", location: "+this.getLocation()+", action: "+getAction();
    }


}
