package fr.marc.patientInfo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientInfo.model.Patient;
import fr.marc.patientInfo.service.IPatientInfoService;

@RestController
public class PatientInfoController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientInfoController.class); 
	
	@Autowired
	private IPatientInfoService patientInfoService;
	
	/**
	 * To get the list of all patients
	 * @return the list of all patients
	 */
	@GetMapping("/PatientInfo/list")
	public Iterable<Patient> getPatients(){
		log.info("GET /PatientInfo/list => list of all patients");
		return patientInfoService.getPatients();
	}
	
	/**
	 * To get a patient according to an id
	 * @param id
	 * @return the patient according to this id if exist
	 */
	@GetMapping("/PatientInfo/byId")
	public Optional<Patient> getPatientById (@RequestParam Integer id){
		log.info("Get the patient with id = {}",id);
		return patientInfoService.getPatientById(id);
    }
	
	/**
	 * To get the list of patients according to a Last name and First name
	 * @param family : Last name in the HL7 (Health Level Seven) standard
	 * @param given : First name in the HL7 (Health Level Seven) standard
	 * @return the list of patients according to this Last name and First name if exist
	 */
	@GetMapping("/PatientInfo/byName")
	public List<Patient> getPatientsByFamilyAndGiven(
			@RequestParam String family,
			@RequestParam String given)
	{
		return patientInfoService.getPatientsByFamilyAndGiven(family, given);
	}
	
	// TODO Update patient
	/**
	 * 
	 * @param id : The id of the patient you want to update
	 * @param patient : New patient data 
	 * @return The patient updated
	 * 			
	 */
	@PostMapping("/PatientInfo/update")
	public Patient updatePatient (@RequestParam Integer id, @RequestBody Patient patient) {
		return patientInfoService.updatePatient(id, patient);
	}
	
	@PostMapping("/PatientInfo/add")
	public Patient createPatient (@RequestBody Patient patient) {
		return patientInfoService.createPatient(patient);
	}
}
