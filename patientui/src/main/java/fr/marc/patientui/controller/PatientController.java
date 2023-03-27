package fr.marc.patientui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.beans.PatientBeanDTO;
import fr.marc.patientui.proxies.PatientInfoProxy;
import jakarta.validation.Valid;

@Controller
public class PatientController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientController.class); 
	private final PatientInfoProxy patientInfoProxy;
	
	public PatientController(PatientInfoProxy patientInfoProxy) {
		super();
		this.patientInfoProxy = patientInfoProxy;
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
		return "PatientInfo";
	}
	
	@GetMapping("/PatientUpdate")
	public String patientUpdatePage(@RequestParam Integer id, Model model){
		PatientBean patient = patientInfoProxy.getPatientById(id);
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
	
}
