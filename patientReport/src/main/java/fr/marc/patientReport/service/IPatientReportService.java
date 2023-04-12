package fr.marc.patientReport.service;

import java.time.LocalDate;
import java.util.List;

import fr.marc.patientReport.model.Note;
import fr.marc.patientReport.model.Patient;
import fr.marc.patientReport.model.Report;

public interface IPatientReportService {
	
	/**
	 * To calculate diabetes risk
	 * @param patient
	 * @param notes
	 * @param currentDate
	 * @return a report containing patient age and diabetes risk assessment
	 */
	Report calculateDiabetesRisk(Patient patient, List<Note> notes, LocalDate currentDate);

}
