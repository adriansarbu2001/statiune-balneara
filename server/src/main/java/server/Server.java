package server;

import org.springframework.core.io.ClassPathResource;
import repository.*;
import repository.database.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.*;

class Server {
    private static final int p = 10;
    private ExecutorService executorService;
    private LocationsRepository locationsRepository;
    private PaymentsRepository paymentsRepository;
    private PlanificationsRepository planificationsRepository;
    private TreatmentsRepository treatmentsRepository;
    private MaxPatientsRepository maxPatientsRepository;

    public Server(LocationsRepository locationsRepository, PaymentsRepository paymentsRepository, PlanificationsRepository planificationsRepository, TreatmentsRepository treatmentsRepository, MaxPatientsRepository maxPatientsRepository) {
        this.locationsRepository = locationsRepository;
        this.paymentsRepository = paymentsRepository;
        this.planificationsRepository = planificationsRepository;
        this.treatmentsRepository = treatmentsRepository;
        this.maxPatientsRepository = maxPatientsRepository;
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
        MaxPatientsRepository r5 = new MaxPatientsRepositoryDatabase(props);

        // Create the server
        Server server = new Server(r1, r2, r3, r4, r5);

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

        ScheduledExecutorService validationExecutor = Executors.newScheduledThreadPool(1);
        validationExecutor.scheduleAtFixedRate(new ValidationRunnable(this.locationsRepository, this.paymentsRepository, this.planificationsRepository, this.treatmentsRepository, this.maxPatientsRepository), 0, 5, TimeUnit.SECONDS);

        while (true) {
            final Socket connection = socket.accept();
            this.handleRequest(connection);
        }
    }

    private void handleRequest(Socket connection) throws Exception {
        // Do the work and get the result
        executorService.execute(new ServiceRunnable(connection, this.locationsRepository, this.paymentsRepository, this.planificationsRepository, this.treatmentsRepository, this.maxPatientsRepository));
    }
}
