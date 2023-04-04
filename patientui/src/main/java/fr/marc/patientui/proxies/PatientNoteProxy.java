package fr.marc.patientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.NoteBean;


@FeignClient(name = "mediscreen-patientNote", url = "localhost:8082")
public interface PatientNoteProxy {
	
	@GetMapping("/PatientNote/byPatient")
	Iterable<NoteBean> getNotesByPatientId (@RequestParam Integer patId);
	
	@GetMapping("/PatientNote/byId")
	public Optional<NoteBean> getNoteById (@RequestParam String id);
}