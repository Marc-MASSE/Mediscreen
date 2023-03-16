package fr.marc.patientui.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {
	
	public PatientNotFoundException(String message) {

	      super(message);

	  }

}
