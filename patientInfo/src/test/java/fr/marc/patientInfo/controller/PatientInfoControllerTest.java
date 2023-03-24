package fr.marc.patientInfo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.marc.patientInfo.model.Patient;

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
				.andExpect(jsonPath("$[0].family", is("last1")))
				.andExpect(jsonPath("$[1].family", is("last2")))
				.andExpect(jsonPath("$[2].family", is("last3")));
		}
	}
	
	// GET /PatientInfo/byId
	@Nested
	class GetPatientById {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family", is("last1")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").doesNotExist());
		}
	}
	
	// GET /PatientInfo/byName
	@Nested
	class GetPatientByName {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=last1&&given=first1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("last1")));
		}
		
		@Test
		public void two_answers () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=last3&&given=first3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("last3")))
				.andExpect(jsonPath("$[0].dob", is("2003-03-03")))
				.andExpect(jsonPath("$[1].family", is("last3")))
				.andExpect(jsonPath("$[1].dob", is("2004-04-04")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=last5&&given=first5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
		}
	}
	
	// TODO POST /PatientInfo/update
	@Nested
	class UpdatePatient {
		@Test
		public void family_and_given_updated () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("last4")
					.given("first4")
					.dob("2004-04-04")
					.sex("F")
					.address("Address4")
					.phone("444444")
					.build();
			mockMvc.perform(post("/PatientInfo/update?id=4")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family", is("last4")))
				.andExpect(jsonPath("$.given", is("first4")));
		}
		
		@Test
		public void no_patient_with_this_id_should_return_nothing () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("last4")
					.given("first4")
					.dob("2004-04-04")
					.sex("F")
					.address("Address4")
					.phone("444444")
					.build();
			MvcResult result = mockMvc.perform(post("/PatientInfo/update?id=15")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn();
			assertThat(result.getResponse().getContentAsString())
				.isEqualTo("");
		}
		
		@Test
		public void patient_already_exist_should_return_new_patient () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("last1")
					.given("first1")
					.dob("2001-01-01")
					.sex("F")
					.address("Address4")
					.phone("444444")
					.build();
			mockMvc.perform(post("/PatientInfo/update?id=3")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", nullValue()));

		}
	}
	
	
	// TODO POST /PatientInfo/add
	

}
