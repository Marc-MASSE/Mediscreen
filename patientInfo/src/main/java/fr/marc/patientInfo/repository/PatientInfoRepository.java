package fr.marc.patientInfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.marc.patientInfo.model.Patient;

@Repository
public interface PatientInfoRepository extends CrudRepository<Patient, Long> {
	
	/**
	 * To collect a list of patients matching a last name and first name
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @return A list of patients matching this last name and first name
	 */
	List<Patient> findByFamilyAndGiven(String family, String given);
	
	/**
	 * To collect a patient matching a last name, first name and birthday
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @param dob : birthday in the HL7 (Health Level Seven) standard
	 * @return The first patient matching a last name, first name and birthday
	 */
	Optional<Patient> findFirstByFamilyAndGivenAndDob(String family, String given, String dob);
	
	/**
	 * To collect the list of all patients sorted by alphabetical order by last name
	 * @return the list of all patients sorted by alphabetical order by last name
	 */
	Iterable<Patient> findAllByOrderByFamilyAsc();

}
