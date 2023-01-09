package client;

import model.Payment;
import model.Planification;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class ClientThread extends Thread {
    @Override
    public void run() {
        int n = 2;
        // A client who send n requests
        for (int i = 1; i <= n; i++) {
            System.out.println("client start");
            try {
                Socket soc = new Socket("localhost", 8080);

                String cnp = "5070921647382";
                Random r = new Random();
                int idl = r.nextInt(2) + 1;
                int idt = r.nextInt(5) + 1;

                int hour = r.nextInt(23);
                int min = r.nextInt(59);

                // Create a planification
                Planification planification = new Planification(0, "Dorel", cnp, LocalDate.now(), idl, idt, LocalDate.of(2023, 1, 15), LocalTime.of(hour, min, 0));

                // Send planification to server
                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                out.writeObject(planification);
                out.flush();

                // Read the response message and print it
                DataInputStream in = new DataInputStream(soc.getInputStream());
                String response = in.readUTF();
                System.out.println(response);

                int cost = 0;
                if (response.equals("programare reusita")) {
                    cost = Integer.parseInt(in.readUTF());
                }

                // Close everything
                in.close();
                out.close();
                soc.close();

                if (response.equals("programare reusita")) {
                    soc = new Socket("localhost", 8080);
                    // Create a payment
                    Payment payment = new Payment(0, cnp, LocalDate.now(), cost);

                    // Send payment to server
                    out = new ObjectOutputStream(soc.getOutputStream());
                    out.writeObject(payment);
                    out.flush();

                    // Read the response message and print it
                    in = new DataInputStream(soc.getInputStream());
                    response = in.readUTF();
                    System.out.println(response);

                    // Close everything
                    in.close();
                    out.close();
                    soc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("client end\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
