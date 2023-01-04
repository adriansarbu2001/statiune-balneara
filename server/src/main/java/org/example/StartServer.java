package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class StartServer {
    private static final int p = 10;
    private static final Executor exec= Executors.newFixedThreadPool(p);
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        try {
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            String str = dis.readUTF();
            System.out.println("message = " + str);
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
