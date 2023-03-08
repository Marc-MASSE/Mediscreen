package fr.marc.patientInfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	
	private Long id;
	
	private String family;
	
	private String given;
	
	private String dob;
	
	private Sex sex;
	
	public enum Sex {F,M};
	
	private String address;
	
	private String phone;

}
