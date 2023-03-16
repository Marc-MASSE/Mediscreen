package fr.marc.patientui.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String invoqueur, Response reponse) {
		
		if (reponse.status() == 404) {
			return new PatientNotFoundException("Not Found");
		}
		return defaultErrorDecoder.decode(invoqueur, reponse);
	}

}
