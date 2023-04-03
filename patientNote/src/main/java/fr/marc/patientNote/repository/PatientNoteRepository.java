package fr.marc.patientNote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.marc.patientNote.model.Note;

@Repository
public interface PatientNoteRepository extends MongoRepository<Note, String> {
	
	List<Note> findByPatId(Integer patId);

}
