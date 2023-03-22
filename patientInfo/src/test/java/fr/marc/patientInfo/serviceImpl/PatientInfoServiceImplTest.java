package fr.marc.patientInfo.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.marc.patientInfo.model.Patient;
import fr.marc.patientInfo.repository.PatientInfoRepository;
import fr.marc.patientInfo.service.IPatientInfoService;

@ExtendWith(MockitoExtension.class)
public class PatientInfoServiceImplTest {
	
	private IPatientInfoService patientInfoService;
	
	@Mock
	private PatientInfoRepository patientInfoRepository;
	
	private Patient patient1;
	private Patient patient2;
	private Patient patient3;
	
	@Captor
	ArgumentCaptor<Patient> patientCaptor;
	
	
	@BeforeEach
	public void init() {
		patientInfoService = new PatientInfoServiceImpl(patientInfoRepository);
		patient1 = Patient.builder()
				.id(1) // 1L to cast 1 in Long format
				.family("Last1")
				.given("First1")
				.dob("2001-01-01")
				.sex("F")
				.address("Address1")
				.phone("111111")
				.build();
		patient2 = Patient.builder()
				.id(2) // 1L to cast 1 in Long format
				.family("Last2")
				.given("First2")
				.dob("2002-02-02")
				.sex("M")
				.address("Address2")
				.phone("222222")
				.build();
		patient3 = Patient.builder()
				.id(3) // 1L to cast 1 in Long format
				.family("Last3")
				.given("First3")
				.dob("2003-03-03")
				.sex("F")
				.address("Address3")
				.phone("333333")
				.build();
	}
	
	@Test
	public void getPatients_success() {
		when(patientInfoRepository.findAllByOrderByFamilyAsc())
			.thenReturn(Arrays.asList(patient1,patient2,patient3));
		assertThat(patientInfoService.getPatients())
			.contains(patient1)
			.contains(patient2)
			.contains(patient3);
		verify(patientInfoRepository).findAllByOrderByFamilyAsc();
	}
	
	@Nested
	class GetPatientById {
		@Test
		public void success() {
			when(patientInfoRepository.findById(1))
				.thenReturn(Optional.of(patient1));
			assertThat(patientInfoService.getPatientById(1))
				.isEqualTo(Optional.of(patient1));
			verify(patientInfoRepository).findById(1);
		}
		
		@Test
		public void no_patient() {
			when(patientInfoRepository.findById(5))
				.thenReturn(Optional.of(new Patient()));
			assertThat(patientInfoService.getPatientById(5))
				.isEqualTo(Optional.of(new Patient()));
			verify(patientInfoRepository).findById(5);
		}
	}
	
	@Nested
	class GetPatientsByFamilyAndGiven {
		@Test
		public void success() {
			when(patientInfoRepository.findByFamilyAndGiven("Last1","First1"))
				.thenReturn(Arrays.asList(patient1));
			assertThat(patientInfoService.getPatientsByFamilyAndGiven("Last1","First1"))
				.contains(patient1);
			verify(patientInfoRepository).findByFamilyAndGiven("Last1","First1");
		}
		
		@Test
		public void no_patient() {
			when(patientInfoRepository.findByFamilyAndGiven("Last5","First5"))
				.thenReturn(new ArrayList<>());
			assertThat(patientInfoService.getPatientsByFamilyAndGiven("Last5","First5"))
				.isEmpty();
			verify(patientInfoRepository).findByFamilyAndGiven("Last5","First5");
		}
	}
	
	@Nested
	class GetPatientByFamilyAndGivenAndDob {
		@Test
		public void success() {
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.thenReturn(Optional.of(patient1));
			assertThat(patientInfoService.getPatientByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.isEqualTo(Optional.of(patient1));
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01");
		}
		
		// TODO getPatientByFamilyAndGivenAndDob test for patient doen't exist
		@Test
		public void no_patient() {
			
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last5","First5","2005-05-05"))
				.thenReturn(Optional.of(new Patient()));
			assertThat(patientInfoService.getPatientByFamilyAndGivenAndDob("Last5","First5","2005-05-05"))
				.isEqualTo(Optional.of(new Patient()));
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last5","First5","2005-05-05");
		}
	}
	
	// TODO updatePatient test
	
	
	// TODO createPatient test
	/*
	@Nested
	class CreatePatient {
		@Test
		public void success() {
			Patient patientToAdd = Patient.builder()
					.family("Last3")
					.given("First3")
					.dob("2003-03-03")
					.sex("F")
					.address("Address3")
					.phone("333333")
					.build();
			when(patientInfoRepository.save(patientToAdd))
				.thenReturn(patient3);
			patientInfoService.createPatient(patientToAdd);
			verify(patientInfoRepository).save(patientCaptor.capture());
			assertThat(patientCaptor.getValue())
				.isEqualTo(patient3);
			verify(patientInfoRepository).save(patientToAdd);
		}
		
		// TODO createPatient test for patient already exist
		
	}
	*/
	
}
