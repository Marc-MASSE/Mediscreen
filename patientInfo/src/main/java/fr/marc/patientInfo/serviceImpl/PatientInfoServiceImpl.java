package fr.marc.patientInfo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.marc.patientInfo.model.Patient;
import fr.marc.patientInfo.repository.PatientInfoRepository;
import fr.marc.patientInfo.service.IPatientInfoService;

@Service
public class PatientInfoServiceImpl implements IPatientInfoService {
	
	private static final Logger log = LoggerFactory.getLogger(PatientInfoServiceImpl.class); 

	private PatientInfoRepository patientInfoRepository;
	
	public PatientInfoServiceImpl(PatientInfoRepository patientInfoRepository) {
		this.patientInfoRepository = patientInfoRepository;
	}

	/**
	 * To collect the list of all patients
	 * @return the list of all patients
	 */
	@Override
	public Iterable<Patient> getPatients(){
		log.info("Get all patients");
		return patientInfoRepository.findAllByOrderByFamilyAsc();
	}

	/**
	 * To find a patient according to an id
	 * @param id
	 * @return the patient according to this id
	 * 			null is there is no patient with this id
	 */
	@Override
	public Optional<Patient> getPatientById(Integer id){
		log.info("Get the patient with id = {}",id);
		Optional<Patient> patient = patientInfoRepository.findById(id);
		if (patient.isPresent()) {
			return patient;
		}
		log.error("There is no patient with id = {} ",id);
		return null;
	}

	/**
	 * To find the list of patients according to a Last name and First name
	 * @param family = Last name in the HL7 (Health Level Seven) standard
	 * @param given = First name in the HL7 (Health Level Seven) standard
	 * @return the list of patients according to this Last name and First name
	 * 			new Patient() if there is no patient according to this Last name and First name
	 */
	@Override
	public List<Patient> getPatientsByFamilyAndGiven(String family, String given){
		log.info("Get the patient {} {}",family, given);
		List<Patient> patientList = patientInfoRepository.findByFamilyAndGiven(family, given);
		if (patientList.isEmpty()) {
			return new ArrayList<Patient>();
		}
		return patientList;
	}

	/**
	 * To find a patient matching a last name, first name and birthday
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @param dob : birthday in the HL7 (Health Level Seven) standard
	 * @return The first patient matching a last name, first name and birthday
	 */
	@Override
	public Optional<Patient> getPatientByFamilyAndGivenAndDob(String family, String given, String dob){
		log.info("Get the patient {} {} {}", family, given, dob);
		return patientInfoRepository.findFirstByFamilyAndGivenAndDob(family, given, dob);
	}
	
	/**
	 * To update a patient designated by his id.
	 * @param id : The id of the patient you want to update
	 * @param patient : New patient data 
	 * @return The patient updated
	 * 			null is there is no patient with this id
	 * 			new Patient() if the data match another patient 
	 */
	@Override
	public Patient updatePatient(Integer patientId, Patient updatedPatient){
		log.info("Update the patient with id = {}",patientId);
		
		Optional<Patient> currentPatient = patientInfoRepository.findById(patientId);
		if (currentPatient.isEmpty()) {
			return null;
		}
		// Test if the patient to update already exist
		Optional<Patient> wantedPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(
				updatedPatient.getFamily(),
				updatedPatient.getGiven(), 
				updatedPatient.getDob());
		if (wantedPatient.isPresent() && !wantedPatient.get().getId().equals(patientId)) {
			return new Patient();
		}
		currentPatient.get().setFamily(updatedPatient.getFamily());
		currentPatient.get().setGiven(updatedPatient.getGiven());
		currentPatient.get().setDob(updatedPatient.getDob());
		currentPatient.get().setSex(updatedPatient.getSex());
		currentPatient.get().setAddress(updatedPatient.getAddress());
		currentPatient.get().setPhone(updatedPatient.getPhone());
		return patientInfoRepository.save(currentPatient.get());
	}

	/**
	 * To create a new patient
	 * @param patient
	 * @return The patient created
	 * 			null if this patient already exist
	 */
	@Override
	public Patient createPatient(Patient patient){
		Optional<Patient> findingPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(patient.getFamily(),patient.getGiven(), patient.getDob());
		if (findingPatient.isPresent()) {
			return new Patient();
		}
		return patientInfoRepository.save(patient);
	}



}
