package fr.marc.patientInfo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientInfo.exceptions.PatientNotFoundException;
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
	public ResponseEntity<?> getPatientById (@RequestParam Long id){
		log.info("Get the patient with id = {}",id);
        Optional<Patient> patient = patientInfoService.getPatientById(id);
        if (patient.isPresent()) {
        	return ResponseEntity.ok(patient.get());
        }
        log.error("There is no patient with id = {} ",id);
        // TODO La méthode du cours ne permet pas de renvoyer une réponse personnalisée :
        // La réponse HTTP envoyée par l'application a été remplacée par une réponse 
        // par défaut du conteneur de servlet, qui inclut le message "Not Found" et d'autres 
        // informations par défaut.
        // throw new PatientNotFoundException("There is no patient with id = " + id);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("There is no patient with id = " + id);
    }
	
	/**
	 * To get the list of patients according to a Last name and First name
	 * @param family = Last name in the HL7 (Health Level Seven) standard
	 * @param given = First name in the HL7 (Health Level Seven) standard
	 * @return the list of patients according to this Last name and First name if exist
	 */
	@GetMapping("/PatientInfo/byName")
	public List<Patient> getPatientsByFamilyAndGiven (
			@RequestParam String family, 
			@RequestParam String given){
		log.info("Get the patient {} {}",family, given);
		List<Patient> patientList = patientInfoService.getPatientsByFamilyAndGiven(family, given);
		if (patientList.isEmpty()) {
			log.error("There is no patient {} {}",family,given);
	        throw new PatientNotFoundException("There is no patient " + family + " " + given);
		}
		return patientList;
	}
	
	
	
	

}
