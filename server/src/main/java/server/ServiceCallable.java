package server;

import model.Location;
import model.Payment;
import model.Planification;
import model.Treatment;
import repository.LocationsRepository;
import repository.PaymentsRepository;
import repository.PlanificationsRepository;
import repository.TreatmentsRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import java.time.LocalTime;

public class ServiceCallable implements Callable<String> {
    private final LocationsRepository locationsRepository;
    private final PaymentsRepository paymentsRepository;
    private final PlanificationsRepository planificationsRepository;
    private final TreatmentsRepository treatmentsRepository;
    private final Socket socket;

    public ServiceCallable(LocationsRepository locationsRepository, PaymentsRepository paymentsRepository, PlanificationsRepository planificationsRepository, TreatmentsRepository treatmentsRepository, Socket connection) {
        this.locationsRepository = locationsRepository;
        this.paymentsRepository = paymentsRepository;
        this.planificationsRepository = planificationsRepository;
        this.treatmentsRepository = treatmentsRepository;
        this.socket = connection;
    }

    public static boolean areOverlaping(LocalTime t1, int d1, LocalTime t2, int d2) {
        return t1.isBefore(t2.plusMinutes(d2)) || t2.plusMinutes(d1).isAfter(t2);
    }

    public synchronized String call() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Object object = objectInputStream.readObject();
        objectInputStream.close();
        if (object instanceof Planification planification) {
            Treatment t2 = treatmentsRepository.find(planification.getIdt());
            Location l2 = locationsRepository.find(planification.getIdl());
            int d2 = t2.getDurationMinutes();
            int contor = 0;
            for (Planification el : this.planificationsRepository.getAll()) {
                Treatment t1 = treatmentsRepository.find(el.getIdt());
                Location l1 = locationsRepository.find(el.getIdl());
                int d1 = t1.getDurationMinutes();

                if (el.getTreatmentDate() == planification.getTreatmentDate() && l1.getId() == l2.getId() && areOverlaping(el.getTreatmentTime(), d1, planification.getTreatmentTime(), d2)) {
                    contor++;
                }
            }

            String toReturn;
            if (contor < t2.getMaxPatients()) {
                planificationsRepository.add(planification);
                System.out.println("REUSITA");
                toReturn = "programare reusita";
            } else {
                System.out.println("NEREUSIT");
                toReturn = "programare nereusita";
            }

            try {
                socket.close();
            } catch (IOException ioe) {
                System.out.println("Error closing client connection");
            }
            return toReturn;
        } else if (object instanceof Payment payment) {
            return "Not implemented!";
        } else {
            return "Comanda necunoscuta!";
        }
    }
}
