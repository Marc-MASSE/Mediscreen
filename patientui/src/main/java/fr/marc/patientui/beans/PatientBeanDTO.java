package fr.marc.patientui.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientBeanDTO {

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String family;

	@NotBlank(message = "First name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String given;
}
