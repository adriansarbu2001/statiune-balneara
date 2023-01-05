package server;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class StartServer {
    private static final int p = 10;
    private static final Executor exec= Executors.newFixedThreadPool(p);

    /**
     * Bean parameter for repository construction
     * @return Properties
     */
    @Bean(name="jdbcProps")
    public static Properties createJdbcValues() {
        Properties jdbcProps = new Properties();
        try {
            jdbcProps.load(new ClassPathResource("repo.properties").getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcProps;
    }

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
