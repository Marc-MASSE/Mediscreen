package fr.marc.patientui.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.proxies.PatientNoteProxy;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {
	
	@Autowired
	private NoteController noteController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientNoteProxy patientNoteProxy;
	
	private static final Logger log = LoggerFactory.getLogger(NoteControllerTest.class); 
	private NoteBean note1;
	private NoteBean note2;
	
	@BeforeEach
	public void init() {
		note1 = NoteBean.builder()
				.id("1")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 1, 0, 0, 0))
				.body("Body1")
				.build();
		note2 = NoteBean.builder()
				.id("2")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 2, 0, 0, 0))
				.body("Body2")
				.build();
	}
	
	@Test
	public void displayNoteListPage() throws Exception {
		when(patientNoteProxy.getNotesByPatientId(1))
			.thenReturn(List.of(note1, note2));
		mockMvc.perform(get("/NoteList?patId=1"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("NoteList"))
        	.andExpect(content().string(containsString("Notes")))
        	.andExpect(content().string(containsString("Body1")))
        	.andExpect(content().string(containsString("Body2")));
	}

}
