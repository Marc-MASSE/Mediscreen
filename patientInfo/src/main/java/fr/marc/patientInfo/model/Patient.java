package fr.marc.patientInfo.model;


import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
