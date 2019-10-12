package server;

import app.repositories.QuotesRepository;
import app.repositories.QuotesRepositoryInterface;
import clientportal.Client;
import clientportal.ClientPortalAdapter;
import framework.inject.DependencyInjectionEngine;
import framework.inject.DependencyInjectionContainer;
import framework.services.RouteLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) throws IOException {
        RouteLoader routeLoader = RouteLoader.getInstance();

        DependencyInjectionContainer dependencyInjectionContainer = new DependencyInjectionContainer();
        DependencyInjectionEngine dependencyInjectionEngine = new DependencyInjectionEngine(dependencyInjectionContainer);
        dependencyInjectionEngine.loadClasses();

        //TODO: Service provider
        dependencyInjectionEngine.getDependencyInjectionContainer().bind(QuotesRepositoryInterface.class, QuotesRepository.class);
        dependencyInjectionEngine.getDependencyInjectionContainer().bind(Client.class, ClientPortalAdapter.class);

        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is running at http://localhost:"+TCP_PORT);
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket, dependencyInjectionEngine)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
