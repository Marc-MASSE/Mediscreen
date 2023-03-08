package fr.marc.patientInfo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.marc.patientInfo.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

}
