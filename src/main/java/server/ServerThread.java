package server;

import framework.route.Route;
import framework.services.ControllerDispatcher;
import framework.services.DependencyInjectionContainer;
import framework.services.RouteLoader;
import framework.request.enums.Method;
import framework.request.Header;
import framework.request.Helper;
import framework.request.Request;
import framework.request.exceptions.RequestNotValidException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket socket){
        this.socket = socket;

        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        try {

            Request request = this.generateRequest();
            DependencyInjectionContainer container = new DependencyInjectionContainer();
            container.bind(Request.class, request);

            Route route = RouteLoader.getInstance().getRoute(request.getMethod(), request.getLocation());

            try {
                new ControllerDispatcher(container).dispatch(route);
            } catch (ClassNotFoundException e) {
                System.err.println("Class "+route.getAction().split("@")[0]+" does not exist");
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                System.err.println("Method "+route.getAction().split("@")[1]+" does not exist");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access to "+route.getAction());
                e.printStackTrace();
            } catch (InstantiationException e) {
                System.err.println("Can not instantiate "+route.getAction().split("@")[0]);
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                System.err.println("Illegal access to "+route.getAction());
                e.printStackTrace();
            }

//            out.println(response);

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestNotValidException e) {
            e.printStackTrace();
        }
    }

    private Request generateRequest() throws IOException, RequestNotValidException {
        String command = in.readLine();
        if(command == null) {
            throw new RequestNotValidException(command);
        }

        String[] actionRow = command.split(" ");
        Method method = Method.valueOf(actionRow[0]);
        String route = actionRow[1];
        Header header = new Header();
        HashMap<String, String> parameters = Helper.getParametersFromRoute(route);

        do {
            System.out.println(command);
            command = in.readLine();
            String[] headerRow = command.split(": ");
            if(headerRow.length == 2) {
                header.add(headerRow[0], headerRow[1]);
            }
        } while(!command.trim().equals(""));

        if(method.equals(Method.POST)) {
            int contentLength = Integer.parseInt(header.get("content-length"));
            char[] buff = new char[contentLength];
            in.read(buff, 0, contentLength);
            String parametersString = new String(buff);

            HashMap<String, String> postParameters = Helper.getParametersFromString(parametersString);
            for (String parameterName : postParameters.keySet()) {
                parameters.put(parameterName, postParameters.get(parameterName));
            }
        }

        Request request = new Request(method, route, header, parameters);

        return request;
    }

    private String napraviOdogovor(){
        String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
//		String vrednost = komanda.substring(komanda.indexOf("poljeForme=")+11, komanda.indexOf("HTTP")-1);

        retVal += "<html><head><title>Odgovor servera</title><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n</head>\n";
        retVal += "<body><h1>Uneta vrednost: "+1+"</h1>\n";
        retVal += "</body></html>";

//        System.out.println("HTTP odgovor:");
//        System.out.println(retVal);

        return retVal;
    }
}
