package fr.marc.patientNote.service;

import java.util.Optional;

import fr.marc.patientNote.model.Note;

public interface IPatientNoteService {
	
	/**
	 * To collect all notes of a patient identified by his id
	 * @param patId : The patient id
	 * @return The note list of a patient identified by his id
	 */
	Iterable<Note> getNotesByPatientId(Integer patId);
	
	/**
	 * To get the note identified by its id
	 * @param id : the note id
	 * @return The note identified by its id
	 */
	Optional<Note> getNoteById(String id);
	
	/**
	 * To update a note designated by his id
	 * @param id : The id of the note you want to update
	 * @param note : New note data
	 * @return The note updated
	 */
	Note updateNote(String id, Note note);
	
	/**
	 * To create a new note
	 * @param id : The id of the patient for whom we want to create a note
	 * @param note : The note containing the body we want to create
	 * @return The note created
	 */
	Note createNote(Integer patId,Note note);
	
}
