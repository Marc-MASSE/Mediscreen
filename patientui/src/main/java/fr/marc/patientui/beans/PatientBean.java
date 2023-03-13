package fr.marc.patientui.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {
	
	private Long id;
	
	// Last name in the HL7 (Health Level Seven) standard
	private String family;
	
	// First name in the HL7 (Health Level Seven) standard
	private String given;
	
	// Birthday in the HL7 (Health Level Seven) standard
	private String dob;
	
	// Two values : F or M
	private String sex;
	
	private String address;
	
	private String phone;

}
