package fr.marc.patientInfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

    public PatientNotFoundException(String s) {
        super(s);
    }
}
