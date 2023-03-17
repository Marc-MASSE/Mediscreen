package fr.marc.patientInfo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.marc.patientInfo.exceptions.PatientNotFoundException;
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
	public Iterable<Patient> getPatients() {
		log.info("Get all patients");
		return patientInfoRepository.findAllByOrderByFamilyAsc();
	}

	/**
	 * To find a patient according to an id
	 * @param id
	 * @return the patient according to this id
	 */
	@Override
	public Optional<Patient> getPatientById(Integer id) {
		log.info("Get the patient with id = {}",id);
		/*
		Optional<Patient> patient = patientInfoRepository.findById(id);
		if (patient.isPresent()) {
			return patient;
		}
		log.error("There is no patient with id = {} ",id);
        throw new PatientNotFoundException("There is no patient with id = " + id);
        */
		return patientInfoRepository.findById(id);
	}

	/**
	 * To find the list of patients according to a Last name and First name
	 * @param family = Last name in the HL7 (Health Level Seven) standard
	 * @param given = First name in the HL7 (Health Level Seven) standard
	 * @return the list of patients according to this Last name and First name
	 */
	@Override
	public List<Patient> getPatientsByFamilyAndGiven(String family, String given) {
		log.info("Get the patient {} {}",family, given);
		/*
		List<Patient> patientList = patientInfoRepository.findByFamilyAndGiven(family, given);
		if (patientList.isEmpty()) {
			log.error("There is no patient {} {}",family,given);
	        throw new PatientNotFoundException("There is no patient " + family + " " + given);
		}
		return patientList;
		*/
		return patientInfoRepository.findByFamilyAndGiven(family, given);
	}

	/**
	 * To find a patient matching a last name, first name and birthday
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @param dob : birthday in the HL7 (Health Level Seven) standard
	 * @return The first patient matching a last name, first name and birthday
	 */
	@Override
	public Optional<Patient> getPatientByFamilyAndGivenAndDob(String family, String given, String dob) {
		log.info("Get the patient {} {} {}",family, given, dob);
		return patientInfoRepository.findFirstByFamilyAndGivenAndDob(family, given, dob);
	}
	
	@Override
	public Patient updatePatient(Patient patient) {
		log.info("Update the patient {} {}",patient.getFamily(),patient.getGiven());
		if (patientInfoRepository.findById(patient.getId()).isEmpty()) {
			log.error("There is no patient with id = {} ",patient.getId());
			throw new PatientNotFoundException("There is no patient with id = " + patient.getId());
		}
		// Test if the patient to update already exist
		Optional<Patient> findingPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(patient.getFamily(),patient.getGiven(), patient.getDob());
		if (findingPatient.isPresent()&&!findingPatient.get().getId().equals(patient.getId())) {
			log.info("The patient {} {} {} already exist",patient.getFamily(),patient.getGiven(), patient.getDob());
			throw new PatientNotFoundException("The patient " + patient.getFamily() + " " + patient.getGiven() + " " + patient.getDob() + " already exist");
		}
		return patientInfoRepository.save(patient);
	}

	@Override
	public Patient createPatient(Patient patient) {
		// Test if the patient to update already exist
		Optional<Patient> findingPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(patient.getFamily(),patient.getGiven(), patient.getDob());
		if (findingPatient.isPresent()&&!findingPatient.get().getId().equals(patient.getId())) {
			log.info("The patient {} {} {} already exist",patient.getFamily(),patient.getGiven(), patient.getDob());
			throw new PatientNotFoundException("The patient " + patient.getFamily() + " " + patient.getGiven() + " " + patient.getDob() + " already exist");
		}
		return patientInfoRepository.save(patient);
	}



}
