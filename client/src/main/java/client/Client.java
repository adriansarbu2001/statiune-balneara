package client;

import model.Payment;
import model.Planification;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        int i, p = 3;

        List<ClientThread> myThreads = new ArrayList<>();

        for (i = 0; i < p; i++) {
            myThreads.add(new ClientThread());
            myThreads.get(i).start();
        }

        for (i = 0; i < p; i++) {
            myThreads.get(i).join();
        }
    }
}
