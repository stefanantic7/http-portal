package server.request;

import server.enums.Method;

import java.util.HashMap;

public class Request {

    private Method method;
    private String route;
    private Header header;
    private HashMap<String, String> parameters;

    public Request() {
        this(Method.GET, "/");
    }

    public Request(Method method, String route) {
        this(method, route, new Header(), new HashMap<String, String>());
    }

    public Request(Method method, String route, Header header, HashMap<String, String> parameters) {
        this.method = method;
        this.route = route;
        this.header = header;
        this.parameters = parameters;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public HashMap<String, String> getParameters() {
        return new HashMap<String, String>(this.parameters);
    }

    public boolean isMethod(Method method) {
        return this.getMethod().equals(method);
    }

    public Method getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public Header getHeader() {
        return header;
    }
}
