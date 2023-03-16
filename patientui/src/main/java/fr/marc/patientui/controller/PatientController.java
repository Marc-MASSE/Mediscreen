package fr.marc.patientui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.proxies.PatientInfoProxy;

@Controller
public class PatientController {
	
	private final PatientInfoProxy patientInfoProxy;
	
	
	
	public PatientController(PatientInfoProxy patientInfoProxy) {
		super();
		this.patientInfoProxy = patientInfoProxy;
	}


	@RequestMapping("/")
	public String home() {
		return "Home";
	}
	
	@RequestMapping("/PatientList")
	public String patientListPage(Model model) {
		List<PatientBean> patients =  patientInfoProxy.getPatients();
	    model.addAttribute("patients", patients);
		return "PatientList";
	}
	
	@GetMapping("/PatientSearch")
	public String patientSearchPage(
			@RequestParam (name="family", defaultValue="") String family,
			@RequestParam (name="given", defaultValue="") String given,
			@ModelAttribute PatientBean patientBean,
			Model model) {
		List<PatientBean> patients = new ArrayList<>();
		try {
			patients = patientInfoProxy.getPatientsByFamilyAndGiven(family, given);
		} catch (Exception e) {
			
		} finally {
			model.addAttribute("patients",patients);
		}
		return "PatientSearch";
	}
	
	@PostMapping("/PatientSearch")
	public String PatientResearch (@ModelAttribute PatientBean patientBean) {
		return "redirect:/PatientSearch?family="+patientBean.getFamily()+"&&given="+patientBean.getGiven();
	}
	
	@GetMapping("/PatientInfo")
	public String patientInfoPage(@RequestParam Integer id, Model model) {
		PatientBean patient = patientInfoProxy.getPatientById(id);
		model.addAttribute("patient",patient);
		return "PatientInfo";
	}
	
}
