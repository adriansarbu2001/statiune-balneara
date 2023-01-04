package org.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StartClient {
    public static void main(String[] args) {
        try {
            Socket soc = new Socket("localhost", 8080);

            DataOutputStream d = new DataOutputStream(soc.getOutputStream());

            // message to display
            d.writeUTF("Hello world!");

            d.flush();

            // closing DataOutputStream
            d.close();

            // closing socket
            soc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
