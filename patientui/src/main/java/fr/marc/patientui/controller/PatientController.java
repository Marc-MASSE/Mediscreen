package fr.marc.patientui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String home(Model model) {
		List<PatientBean> patients =  patientInfoProxy.getPatients();
	    model.addAttribute("patients", patients);
		return "Home";
	}
}
