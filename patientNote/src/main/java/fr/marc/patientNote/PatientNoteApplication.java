package fr.marc.patientNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;

@SpringBootApplication
@EnableMongoRepositories
public class PatientNoteApplication implements CommandLineRunner {

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	public static void main(String[] args) {
		SpringApplication.run(PatientNoteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		patientNoteRepository.deleteAll();

		// save a couple of notes
		patientNoteRepository.save(new Note(1, "Note 1 for Patient1"));
		patientNoteRepository.save(new Note(2, "Note 1 for Patient2"));

		// fetch all notes
		System.out.println("-------------------------------");
		System.out.println("Notes found with findAll():");
		System.out.println("-------------------------------");
		for (Note note : patientNoteRepository.findAll()) {
			System.out.println(note);
		}
		System.out.println();
	}

}
