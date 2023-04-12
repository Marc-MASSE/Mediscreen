package fr.marc.patientNote.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;
import fr.marc.patientNote.service.IPatientNoteService;

@Service
public class PatientNoteServiceImpl implements IPatientNoteService {

	private static final Logger log = LoggerFactory.getLogger(PatientNoteServiceImpl.class); 

	private PatientNoteRepository patientNoteRepository;
	
	public PatientNoteServiceImpl(PatientNoteRepository patientNoteRepository) {
		this.patientNoteRepository = patientNoteRepository;
	}

	/**
	 * To collect all notes of a patient identified by his id
	 * @param patId : The patient id
	 * @return The note list of a patient identified by his id
	 * 			new Note() if there is no note according to this id
	 */
	@Override
	public Iterable<Note> getNotesByPatientId(Integer patId) {
		log.info("Get notes for patient with id = {}",patId);
		List<Note> noteList = patientNoteRepository.findByPatIdOrderByDateDesc(patId);
		if (noteList.isEmpty()){
			return new ArrayList<Note>();
		}
		return noteList;
	}

	/**
	 * To get the note identified by its id
	 * @param id : the note id
	 * @return The note identified by its id
	 * 			null if there is no note according to this id
	 */
	@Override
	public Optional<Note> getNoteById(String id) {
		log.info("Get note with id = {}",id);
		Optional<Note> note = patientNoteRepository.findById(id);
		if (note.isPresent()) {
			return note;
		}
		log.error("There is no note with id = {} ",id);
		return null;
	}

	/**
	 * To update a note designated by his id
	 * @param id : The id of the note you want to update
	 * @param note : New note data
	 * @return The note updated
	 */
	@Override
	public Note updateNote(String id, Note note) {
		Optional<Note> currentNote = patientNoteRepository.findById(id);
		if (currentNote.isEmpty()) {
			return null;
		}
		currentNote.get().setBody(note.getBody());
		return patientNoteRepository.save(currentNote.get());
	}

	/**
	 * To create a new note
	 * @param id : The id of the patient for whom we want to create a note
	 * @param note : The note containing the body we want to create
	 * @return The note created
	 */
	@Override
	public Note createNote(Integer patId, Note note) {
		Note creatingNote = Note.builder()
				.patId(patId)
				.date(LocalDateTime.now())
				.body(note.getBody())
				.build();
		return patientNoteRepository.save(creatingNote);
	}

}
