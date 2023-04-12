package fr.marc.patientReport.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.marc.patientReport.model.Note;
import fr.marc.patientReport.model.Patient;
import fr.marc.patientReport.model.Report;
import fr.marc.patientReport.model.Triggers;
import fr.marc.patientReport.repository.PatientReportRepository;
import fr.marc.patientReport.service.IPatientReportService;

@Service
public class PatientReportServiceImpl implements IPatientReportService {

	private PatientReportRepository patientReportRepository;
	
	public PatientReportServiceImpl(PatientReportRepository patientReportRepository) {
		this.patientReportRepository = patientReportRepository;
	}

	/**
	 * To calculate diabetes risk
	 * @param patient
	 * @param notes
	 * @param currentDate
	 * @return a report containing patient age and diabetes risk assessment
	 */
	@Override
	public Report calculateDiabetesRisk(Patient patient, List<Note> notes, LocalDate currentDate) {
		Integer age = calculateAge(patient, currentDate);
		Integer triggersNumber = calculateTriggersNumber(notes);
		Report report = Report.builder()
				.age(age)
				.assessment(assessTheRisk(patient.getSex(),age,triggersNumber))
				.build();
		return report;
	}
	
	/**
	 * To calculate the age of a patient
	 * @param patient
	 * @param currentDate
	 * @return the age as an Integer
	 */
	Integer calculateAge(Patient patient, LocalDate currentDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthdate = LocalDate.parse(patient.getDob(), formatter);
		return (int) ChronoUnit.YEARS.between(birthdate, currentDate);
	}
	
	/**
	 * To calculate the number of trigger terms in a note list
	 * @param notes : A note list
	 * @return The number of trigger terms
	 */
	Integer calculateTriggersNumber(List<Note> notes) {
		Set<String> triggersTermFound = new HashSet<String>();
		Iterable<Triggers> triggersList = patientReportRepository.findAll();
		for (Note note : notes) {
			for (Triggers triggers : triggersList) {
				String term = triggers.getTerm();
				if (note.getBody().toLowerCase().contains(term)){
					triggersTermFound.add(term);
				}
			}
		}
		return triggersTermFound.size();
	}
	
	String assessTheRisk(String sex, Integer age, Integer triggersNumber) {
		if (sex.equals("F") && age <= 30) {
			if (triggersNumber > 3 && triggersNumber < 7) {return "Danger";}
			if (triggersNumber > 6) {return "Early onset";}
		}
		if (sex.equals("M") && age <= 30) {
			if (triggersNumber == 3 || triggersNumber == 4) {return "Danger";}
			if (triggersNumber > 4) {return "Early onset";}
		}
		if (age > 30) {
			if (triggersNumber > 1 && triggersNumber < 6) {return "Borderline";}
			if (triggersNumber == 6 || triggersNumber == 7) {return "Danger";}
			if (triggersNumber > 7) {return "Early onset";}
		}
		return "None";
	}

}
