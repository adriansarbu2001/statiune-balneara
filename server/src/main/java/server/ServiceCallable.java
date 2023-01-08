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
    private final Object command;
    private final LocationsRepository locationsRepository;
    private final PaymentsRepository paymentsRepository;
    private final PlanificationsRepository planificationsRepository;
    private final TreatmentsRepository treatmentsRepository;

    public ServiceCallable(Object command, LocationsRepository locationsRepository, PaymentsRepository paymentsRepository, PlanificationsRepository planificationsRepository, TreatmentsRepository treatmentsRepository) {
        this.command = command;
        this.locationsRepository = locationsRepository;
        this.paymentsRepository = paymentsRepository;
        this.planificationsRepository = planificationsRepository;
        this.treatmentsRepository = treatmentsRepository;
    }

    public static boolean areOverlaping(LocalTime t1, int d1, LocalTime t2, int d2) {
        return t1.isBefore(t2.plusMinutes(d2)) || t2.plusMinutes(d1).isAfter(t2);
    }

    public synchronized String call() throws Exception {
        if (command instanceof Planification planification) {
            Treatment t2 = treatmentsRepository.find(planification.getIdt());
            Location l2 = locationsRepository.find(planification.getIdl());
            int d2 = t2.getDurationMinutes();

            int count = 0;

            for (Planification el : this.planificationsRepository.getAll()) {
                Treatment t1 = treatmentsRepository.find(el.getIdt());
                Location l1 = locationsRepository.find(el.getIdl());
                int d1 = t1.getDurationMinutes();

                // if the planification is in the same location and at the same treatment
                if (l1.getId() == l2.getId() && t1.getId() == t2.getId()) {

                    // if the planification is at the same date and at the same time
                    if (el.getTreatmentDate().isEqual(planification.getTreatmentDate()) && areOverlaping(el.getTreatmentTime(), d1, planification.getTreatmentTime(), d2)) {

                        // count how many planifications are overlaping
                        count += 1;
                    }
                }
            }

            // if more planifications are overlaping than the maximum number of patients that can be accepted at the same time, then the planification is unsuccessful
            if (count < t2.getMaxPatients()) {
                planificationsRepository.add(planification);
                return "programare reusita";
            } else {
                return "programare nereusita";
            }
        } else if (command instanceof Payment payment) {

            return "Not implemented!";

        } else {
            return "Comanda necunoscuta!";
        }
    }
}
