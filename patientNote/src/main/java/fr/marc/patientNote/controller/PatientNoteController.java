package fr.marc.patientNote.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.service.IPatientNoteService;

@RestController
public class PatientNoteController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientNoteController.class); 

	@Autowired
	private IPatientNoteService patientNoteService;
	
	/**
	 * To collect all notes of a patient identified by his id
	 * @param patId : The patient id
	 * @return All notes of a patient identified by his id
	 */
	@GetMapping("/PatientNote/byPatient")
	public Iterable<Note> getNotesByPatientId (@RequestParam Integer patId){
		log.info("Get notes fore patient with id = {}",patId);
		return patientNoteService.getNotesByPatientId(patId);
	}
	
	/**
	 * To get the note identified by its id
	 * @param id : The note id
	 * @return The note identified by its id
	 */
	@GetMapping("/PatientNote/byId")
	public Optional<Note> getNoteById (@RequestParam String id){
		log.info("Get note with id = {}",id);
		return patientNoteService.getNoteById(id);
	}
	
	/**
	 * To update the note identified by its id
	 * @param id : The note id
	 * @param note
	 * @return The updated note
	 */
	@PostMapping("/PatientNote/update")
	public Note updateNote (
			@RequestParam String id,
			@RequestBody Note note)
	{
		return patientNoteService.updateNote(id, note);
	}
	
	@PostMapping("/PatientNote/create")
	public Note createNote (
			@RequestParam Integer patId,
			@RequestBody Note note)
	{
		return patientNoteService.createNote(patId, note);
	}
}
