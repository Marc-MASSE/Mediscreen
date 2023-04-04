package fr.marc.patientNote.serviceImpl;

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

	@Override
	public Iterable<Note> getNotesByPatientId(Integer patId) {
		log.info("Get notes for patient with id = {}",patId);
		List<Note> noteList = patientNoteRepository.findByPatIdOrderByDateDesc(patId);
		if (noteList.isEmpty()){
			return new ArrayList<Note>();
		}
		return noteList;
	}

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

}
