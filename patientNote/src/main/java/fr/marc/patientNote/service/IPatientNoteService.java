package fr.marc.patientNote.service;

import java.util.Optional;

import fr.marc.patientNote.model.Note;

public interface IPatientNoteService {
	
	Iterable<Note> getNotesByPatientId(Integer patId);
	
	Optional<Note> getNoteById(String id);
	
}
