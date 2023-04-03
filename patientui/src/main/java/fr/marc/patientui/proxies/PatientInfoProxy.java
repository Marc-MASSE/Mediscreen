package fr.marc.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.PatientBean;

@FeignClient(name = "mediscreen-patientInfo", url = "localhost:8081")
public interface PatientInfoProxy {
	
	@GetMapping(value = "/PatientInfo/list")
	List<PatientBean> getPatients();
	
	@GetMapping(value = "/PatientInfo/byId")
	PatientBean getPatientById (@RequestParam Integer id);
	
	@GetMapping(value = "/PatientInfo/byName")
	List<PatientBean> getPatientsByFamilyAndGiven(
			@RequestParam String family, 
			@RequestParam String given);

	@PostMapping("/PatientInfo/update")
	public PatientBean updatePatient (
			@RequestParam Integer id, 
			@RequestBody PatientBean patient);
}
