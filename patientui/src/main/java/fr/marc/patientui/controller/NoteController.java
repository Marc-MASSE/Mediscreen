package fr.marc.patientui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.proxies.PatientNoteProxy;

@Controller
public class NoteController {
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class); 
	private final PatientNoteProxy patientNoteProxy;

	public NoteController(PatientNoteProxy patientNoteProxy) {
		this.patientNoteProxy = patientNoteProxy;
	}
	
	@GetMapping("/NoteList")
	public String noteListPage(
			@RequestParam Integer patId,
			Model model)
	{
		log.info("Note Attributes: {}", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("notes", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("patientId",patId);
		return "NoteList";
	}

}
