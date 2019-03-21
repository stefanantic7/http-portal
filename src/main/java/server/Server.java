package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is running...");
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
