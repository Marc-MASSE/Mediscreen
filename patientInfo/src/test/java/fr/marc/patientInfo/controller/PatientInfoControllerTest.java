package fr.marc.patientInfo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientInfoControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(PatientInfoControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	
	// GET /PatientInfo/list
	@Nested
	class GetPatients {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/list"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("Last1")))
				.andExpect(jsonPath("$[1].family", is("Last2")))
				.andExpect(jsonPath("$[2].family", is("Last3")));
		}
	}
	
	
	// GET /PatientInfo/byId
	@Nested
	class GetPatientById {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family", is("Last1")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=5"))
				.andExpect(status().is(404))
				.andExpect(jsonPath("$.message", is("There is no patient with id = 5")));
		}
	}
	
	
	// TODO GET /PatientInfo/byName
	@Nested
	class GetPatientByName {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=Last1&&given=First1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("Last1")));
		}
		
		@Test
		public void two_answers () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=Last3&&given=First3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("Last3")))
				.andExpect(jsonPath("$[0].dob", is("2003-03-03")))
				.andExpect(jsonPath("$[1].family", is("Last3")))
				.andExpect(jsonPath("$[1].dob", is("2004-04-04")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=Last5&&given=First5"))
				.andExpect(status().is(404))
				.andExpect(jsonPath("$.message", is("There is no patient Last5 First5")));
		}
	}
	
	
	// TODO POST /PatientInfo/add
	
	
	
	

}
