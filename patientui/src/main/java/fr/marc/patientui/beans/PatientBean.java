package fr.marc.patientui.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {

	private Integer id;

	// Last name in the HL7 (Health Level Seven) standard
	@NotBlank(message = "Last name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String family;

	// First name in the HL7 (Health Level Seven) standard
	@NotBlank(message = "First name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String given;

	// Birthday in the HL7 (Health Level Seven) standard
	@NotBlank(message = "Birthdate is mandatory")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be YYYY-MM-DD")
	private String dob;

	// Two values allowed : F or M
	@NotBlank(message = "Sex is mandatory")
	@Pattern(regexp = "^[FM]$", message = "Only F or M is mandatory")
	@Size(max = 1, message = "1 character maximum are allowed (F or M)")
	private String sex;

	@Size(max = 100, message = "100 characters maximum are allowed")
	private String address;

	@Size(max = 20, message = "100 characters maximum are allowed")
	private String phone;

}
