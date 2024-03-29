package fr.marc.patientInfo.service;

import java.util.List;
import java.util.Optional;

import fr.marc.patientInfo.model.Patient;

public interface IPatientInfoService {
	
	/**
	 * To collect the list of all patients
	 * @return the list of all patients
	 */
	Iterable<Patient> getPatients();
	
	/**
	 * To find a patient according to an id
	 * @param id
	 * @return the patient according to this id
	 */
	Optional<Patient> getPatientById(Integer id);
	
	/**
	 * To find the list of patients according to a Last name and First name
	 * @param family = Last name in the HL7 (Health Level Seven) standard
	 * @param given = First name in the HL7 (Health Level Seven) standard
	 * @return the list of patients according to this Last name and First name
	 */
	List<Patient> getPatientsByFamilyAndGiven(String family, String given);
	
	/**
	 * To find a patient matching a last name, first name and birthday
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @param dob : birthday in the HL7 (Health Level Seven) standard
	 * @return The first patient matching a last name, first name and birthday
	 */
	Optional<Patient> getPatientByFamilyAndGivenAndDob (String family, String given, String dob);
	
	/**
	 * To update a patient designated by his id.
	 * @param id : The id of the patient you want to update
	 * @param patient : New patient data 
	 * @return The patient updated
	 */
	Patient updatePatient(Integer id, Patient patient);
	
	/**
	 * To create a new patient
	 * @param patient
	 * @return The patient created
	 */
	Patient createPatient(Patient patient);
}
