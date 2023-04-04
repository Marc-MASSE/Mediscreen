package fr.marc.patientNote.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.service.IPatientNoteService;

@RestController
public class PatientNoteController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientNoteController.class); 

	@Autowired
	private IPatientNoteService patientNoteService;
	
	@GetMapping("/PatientNote/byPatient")
	public Iterable<Note> getNotesByPatientId (@RequestParam Integer patId){
		log.info("Get notes fore patient with id = {}",patId);
		return patientNoteService.getNotesByPatientId(patId);
	}
	
	@GetMapping("/PatientNote/byId")
	public Optional<Note> getNoteById (@RequestParam String id){
		log.info("Get note with id = {}",id);
		return patientNoteService.getNoteById(id);
	}
	
}
