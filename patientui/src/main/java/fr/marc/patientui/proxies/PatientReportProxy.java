package fr.marc.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientAndNotesBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.beans.ReportBean;

@FeignClient(name = "mediscreen-patientReport", url = "localhost:8083")
public interface PatientReportProxy {
	
	@PostMapping("/PatientReport")
	public ReportBean getReport(@RequestBody PatientAndNotesBean patientAndNotes);
}
