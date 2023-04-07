package fr.marc.patientNote.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientNoteControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(PatientNoteControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientNoteRepository patientNoteRepository;
	
	// GET /PatientNote/byPatient
	@Nested
	class GetNotesByPatientId {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientNote/byPatient?patId=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].body", is("Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level")));
		}
		
		@Test
		public void no_note () throws Exception {
			mockMvc.perform(get("/PatientNote/byPatient?patId=15"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
		}
	}
	
	// GET /PatientNote/byId
	@Nested
	class GetNoteById {
		@Test
		public void success () throws Exception {
			List<Note> notes = patientNoteRepository.findByPatIdOrderByDateDesc(1);
			mockMvc.perform(get("/PatientNote/byId?id=" + notes.get(0).getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is("Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level")));
		}
		
		@Test
		public void no_note () throws Exception {
			mockMvc.perform(get("/PatientNote/byId?id=1"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		}
	}

}
