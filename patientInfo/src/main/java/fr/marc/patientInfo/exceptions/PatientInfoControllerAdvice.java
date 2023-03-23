package fr.marc.patientInfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * To catch the exceptions and return an exception message
 */
@ControllerAdvice
public class PatientInfoControllerAdvice {

	/**
	 * To catch the exception PatientNotFoundException and returns an HTTP response
	 * with status 404 Not Found and the exception message
	 * 
	 * @param ex the PatientNotFoundException
	 * @return this message in JSon format
	 */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message){
            this.status = status;
            this.message = message;
        }

        public int getStatus(){
            return status;
        }

        public String getMessage(){
            return message;
        }
    }
}

