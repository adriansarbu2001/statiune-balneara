package repository;

import model.MaxPatient;

/**
 * Custom interface for the locations repository
 */
public interface MaxPatientsRepository extends Repository<MaxPatient> {
    public MaxPatient findBy(int idl, int idt) throws Exception ;
}
