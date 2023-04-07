package fr.marc.patientui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;

@Controller
public class NoteController {
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class); 
	private final PatientNoteProxy patientNoteProxy;
	private final PatientInfoProxy patientInfoProxy;

	public NoteController(PatientNoteProxy patientNoteProxy, PatientInfoProxy patientInfoProxy) {
		this.patientNoteProxy = patientNoteProxy;
		this.patientInfoProxy = patientInfoProxy;
	}
	
	@GetMapping("/NoteList")
	public String noteListPage(
			@RequestParam Integer patId,
			Model model)
	{
		log.info("Note Attributes: {}", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("notes", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("patient",patientInfoProxy.getPatientById(patId));
		return "NoteList";
	}
	
	@GetMapping("/NoteUpdate")
	public String noteUpdatePage(@RequestParam String id, Model model){
		NoteBean note = patientNoteProxy.getNoteById(id).get();
		model.addAttribute("note",note);
		PatientBean patient = patientInfoProxy.getPatientById(note.getPatId());
		model.addAttribute("patient", patient);
		return "NoteUpdate";
	}
	
	@PostMapping("/NoteUpdate")
	public String noteUpdateValidation(
			@RequestParam String id,
			@ModelAttribute("note") NoteBean note) 
	{
		NoteBean updatedNote = patientNoteProxy.updateNote(id, note);
		log.info("Note Attributes: {}",updatedNote);
		return "redirect:/NoteList?patId=" + updatedNote.getPatId();
	}
	
	@GetMapping("/NoteCreate")
	public String noteCreatePage(@RequestParam Integer patId, Model model){
		NoteBean note = new NoteBean();
		model.addAttribute("note",note);
		PatientBean patient = patientInfoProxy.getPatientById(patId);
		model.addAttribute("patient", patient);
		return "NoteCreate";
	}
	
	@PostMapping("/NoteCreate")
	public String noteCreateValidation(
			@RequestParam Integer patId,
			@ModelAttribute("note") NoteBean note)
	{
		patientNoteProxy.createNote(patId, note);
		return "redirect:/NoteList?patId=" + patId.toString();
	}
	

}
