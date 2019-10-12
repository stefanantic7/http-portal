package framework.services;

import com.google.gson.Gson;
import framework.route.Route;
import framework.request.enums.Method;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RouteLoader {
    private static final String ROUTES_PATH = "src/main/java/app/routes.json";
    private static RouteLoader instance;

    private Route[] routes;

    private RouteLoader() {
        try {
            load(new File(ROUTES_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static RouteLoader getInstance() {
        if (instance == null) {
            synchronized (Route.class) {
                if (instance == null) {
                    instance = new RouteLoader();
                }
            }
        }
        return instance;
    }

    private void load(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        StringBuilder fileContext = new StringBuilder();
        while(scanner.hasNextLine()) {
            fileContext.append(scanner.nextLine());
        }
        this.routes = new Gson().fromJson(String.valueOf(fileContext), Route[].class);
    }

    public Route getRoute(Method method, String location) {
        Route targetRoute = null;
        for (Route route :
                this.routes) {
            if(route.getLocation().equals(location) && route.getMethod().equals(method)) {
                targetRoute = route;
            }
        }

        if(targetRoute == null) {
            //TODO: throw 404 exception
        }

        return targetRoute;
    }

}
