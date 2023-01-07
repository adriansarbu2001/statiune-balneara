package client;

import model.Planification;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class Client {
    public static void main(String[] args) {
        try {
            Socket soc = new Socket("localhost", 8080);

            ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
            DataInputStream in = new DataInputStream(soc.getInputStream());

            Planification p = new Planification(0, "Dorel", "5070921647382", LocalDate.now(), 2, 1, LocalDate.of(2023, 1, 15), LocalTime.of(12, 0, 0));
            out.writeObject(p);

            out.flush();

            System.out.println(in.readUTF());

            in.close();
            out.close();

            soc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
