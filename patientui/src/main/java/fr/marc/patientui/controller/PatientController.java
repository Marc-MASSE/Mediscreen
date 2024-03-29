package fr.marc.patientui.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientAndNotesBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.beans.PatientBeanDTO;
import fr.marc.patientui.beans.ReportBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;
import fr.marc.patientui.proxies.PatientReportProxy;
import jakarta.validation.Valid;

@Controller
public class PatientController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientController.class); 
	private final PatientInfoProxy patientInfoProxy;
	private final PatientNoteProxy patientNoteProxy;
	private final PatientReportProxy patientReportProxy;
	
	public PatientController(
			PatientInfoProxy patientInfoProxy, 
			PatientNoteProxy patientNoteProxy, 
			PatientReportProxy patientReportProxy) {
		this.patientInfoProxy = patientInfoProxy;
		this.patientNoteProxy = patientNoteProxy;
		this.patientReportProxy = patientReportProxy;
	}

	@GetMapping("/")
	public String home() {
		return "Home";
	}
	
	@GetMapping("/PatientList")
	public String patientListPage(Model model){
		List<PatientBean> patients =  patientInfoProxy.getPatients();
	    model.addAttribute("patients", patients);
		return "PatientList";
	}
	
	@GetMapping("/PatientSearch")
	public String patientSearchPage(
			@RequestParam (name="family", defaultValue="") String family,
			@RequestParam (name="given", defaultValue="") String given,
			@ModelAttribute PatientBean patientBean,
			Model model)
	{
		List<PatientBean> patients = patientInfoProxy.getPatientsByFamilyAndGiven(family, given);
		model.addAttribute("patients",patients);

		if (patients.isEmpty() && !family.isEmpty() && !given.isEmpty()) {
			String errorMessage = "Sorry, " + family + " " + given + " is not registered.";
			model.addAttribute("errorMessage",errorMessage);
		}
		return "PatientSearch";
	}
	
	@PostMapping("/PatientSearch")
	public String PatientResearch (
			@Valid 
			@ModelAttribute("patientBean") PatientBeanDTO patientBean,
			BindingResult result)
	{
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientSearch";
        }
		return "redirect:/PatientSearch?family=" + patientBean.getFamily() + "&&given=" + patientBean.getGiven();
	}
	
	@GetMapping("/PatientInfo")
	public String patientInfoPage(@RequestParam Integer id, Model model){
		PatientBean patient = patientInfoProxy.getPatientById(id);
		model.addAttribute("patient",patient);
		PatientAndNotesBean patientAndNotes = PatientAndNotesBean.builder()
				.patient(patient)
				.notes(patientNoteProxy.getNotesByPatientId(id))
				.build();
		model.addAttribute("report",patientReportProxy.getReport(patientAndNotes));
		return "PatientInfo";
	}
	
	@GetMapping("/PatientUpdate")
	public String patientUpdatePage(@RequestParam Integer id, Model model){
		PatientBean patient;
		try {
			patient = patientInfoProxy.getPatientById(id);
		} catch (Exception e) {
			patient = new PatientBean();
		}
		model.addAttribute("patient",patient);
		return "PatientUpdate";
	}
	
	@PostMapping("/PatientUpdate")
	public String PatientUpdateValidation (
			@RequestParam Integer id,
			@Valid 
			@ModelAttribute("patient") PatientBean patient,
			BindingResult result,
			Model model)
	{
		log.info("Patient Attributes: {}", patient);
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientUpdate";
        }
		PatientBean patientResult = patientInfoProxy.updatePatient(id, patient);
		if (patientResult.getId()==null) {
			String errorMessage = "Sorry, " + patient.getFamily() + " " + patient.getGiven() + " already exist.";
			model.addAttribute("errorMessage",errorMessage);
			return "PatientUpdate";
		}
		return "redirect:/PatientInfo?id=" + id;
	}
	
	@GetMapping("/PatientCreate")
	public String patientCreatePage(Model model){
		PatientBean patient = new PatientBean();
		model.addAttribute("patient",patient);
		return "PatientCreate";
	}
	
	@PostMapping("/PatientCreate")
	public String PatientCreateValidation (
			@Valid 
			@ModelAttribute("patient") PatientBean patient,
			BindingResult result,
			Model model)
	{
		log.info("Patient Attributes: {}", patient);
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientCreate";
        }
		PatientBean patientResult = patientInfoProxy.createPatient(patient);
		if (patientResult.getId()==null) {
			String errorMessage = "Sorry, " + patient.getFamily() + " " + patient.getGiven() + " already exist.";
			model.addAttribute("errorMessage",errorMessage);
			return "PatientCreate";
		}
		return "redirect:/PatientInfo?id=" + patientResult.getId();
	}
	
}
