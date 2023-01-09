package server;

import model.Location;
import model.Payment;
import model.Planification;
import model.Treatment;
import repository.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalTime;

public class ValidationRunnable implements Runnable {
    private final LocationsRepository locationsRepository;
    private final PaymentsRepository paymentsRepository;
    private final PlanificationsRepository planificationsRepository;
    private final TreatmentsRepository treatmentsRepository;
    private final MaxPatientsRepository maxPatientsRepository;

    public ValidationRunnable(LocationsRepository locationsRepository, PaymentsRepository paymentsRepository, PlanificationsRepository planificationsRepository, TreatmentsRepository treatmentsRepository, MaxPatientsRepository maxPatientsRepository) throws IOException {
        this.locationsRepository = locationsRepository;
        this.paymentsRepository = paymentsRepository;
        this.planificationsRepository = planificationsRepository;
        this.treatmentsRepository = treatmentsRepository;
        this.maxPatientsRepository = maxPatientsRepository;
    }

    public static boolean areOverlaping(LocalTime t1, int d1, LocalTime t2, int d2) {
        return t1.isBefore(t2.plusMinutes(d2)) || t2.plusMinutes(d1).isAfter(t2);
    }

    public synchronized void run() {
        System.out.println("valid");
    }
}
