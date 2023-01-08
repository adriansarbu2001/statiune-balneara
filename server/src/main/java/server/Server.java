package server;

import org.springframework.core.io.ClassPathResource;
import repository.LocationsRepository;
import repository.PaymentsRepository;
import repository.PlanificationsRepository;
import repository.TreatmentsRepository;
import repository.database.LocationsRepositoryDatabase;
import repository.database.PaymentsRepositoryDatabase;
import repository.database.PlanificationsRepositoryDatabase;
import repository.database.TreatmentsRepositoryDatabase;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Server {
    private static final int p = 10;
    private ExecutorService executorService;
    private LocationsRepository locationsRepository;
    private PaymentsRepository paymentsRepository;
    private PlanificationsRepository planificationsRepository;
    private TreatmentsRepository treatmentsRepository;

    public Server(LocationsRepository locationsRepository, PaymentsRepository paymentsRepository, PlanificationsRepository planificationsRepository, TreatmentsRepository treatmentsRepository) {
        this.locationsRepository = locationsRepository;
        this.paymentsRepository = paymentsRepository;
        this.planificationsRepository = planificationsRepository;
        this.treatmentsRepository = treatmentsRepository;
        this.executorService = Executors.newFixedThreadPool(p);
    }

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

    public static void main(String[] args) {
        // Create the repos
        Properties props = createJdbcValues();
        LocationsRepository r1 = new LocationsRepositoryDatabase(props);
        PaymentsRepository r2 = new PaymentsRepositoryDatabase(props);
        PlanificationsRepository r3 = new PlanificationsRepositoryDatabase(props);
        TreatmentsRepository r4 = new TreatmentsRepositoryDatabase(props);

        // Create the server
        Server server = new Server(r1, r2, r3, r4);

        // Run the server
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        ServerSocket socket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");
        while (true) {
            final Socket connection = socket.accept();
            this.handleRequest(connection);
        }
    }

    private void handleRequest(Socket connection) throws Exception {
        // Read the object from client
        ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
        Object command = in.readObject();

        // Do the work and get the result
        Future<String> result = executorService.submit(new ServiceCallable(command, this.locationsRepository, this.paymentsRepository, this.planificationsRepository, this.treatmentsRepository));
        String res = result.get();
        System.out.println();
        System.out.println(res);

        // Send the result to client
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeUTF(res);
        out.flush();

        // Close everything
        out.close();
        in.close();
        connection.close();
    }
}
