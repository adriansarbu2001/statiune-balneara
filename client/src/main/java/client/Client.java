package client;

import model.Planification;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    public static void main(String[] args) throws InterruptedException {
//a client who send 10 requests
        for(int i=1; i<=10; i++){
            try {
                Socket soc = new Socket("localhost", 8080);

                // Create a planification
                Planification p = new Planification(0, "Dorel", "5070921647382", LocalDate.now(), 2, 3, LocalDate.of(2023, 1, 15), LocalTime.of(12, 0, 0));

                // Send planification to server
                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                out.writeObject(p);
                out.flush();

                // Read the response message and print it
                DataInputStream in = new DataInputStream(soc.getInputStream());
                String response = in.readUTF();
                System.out.println(response);

                // Close everything
                in.close();
                out.close();
                soc.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(2000);
        }

    }
}
