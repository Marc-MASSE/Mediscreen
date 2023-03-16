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
	
	@Autowired
	private PatientInfoController patientInfoController;
	
	// TODO GET /PatientInfo/list
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
	
	
	// TODO GET /PatientInfo/byId
	
	
	
	// TODO GET /PatientInfo/byName
	
	
	
	// TODO POST /PatientInfo/add
	
	
	
	

}
